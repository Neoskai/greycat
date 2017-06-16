/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycat.backup.fastloader;

import com.google.common.base.CharMatcher;
import greycat.BackupOptions;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.tools.FileKey;
import greycat.rocksdb.RocksDBStorage;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FastBackupLoader {

    private Graph _graph;
    private String _folderPath;
    private Map<Integer, Map<Long, FileKey>> _fileMap;

    private List<String> _localFiles;

    private int _poolSize;

    public static final Object LOCK = new Object();

    public FastBackupLoader(String folderPath){
        this(folderPath,
                new GraphBuilder()
                        .withStorage(new RocksDBStorage("data"))
                        .withMemorySize(5000000)
                        .build());
    }

    public FastBackupLoader(String folderPath, Graph graphToUse){
        _folderPath = folderPath;
        _graph = graphToUse;

        _fileMap = new HashMap<>();

        _poolSize = BackupOptions.poolSize();
        _localFiles = new ArrayList<>();
    }

    /**
     * Launch a total backup on the internal graph
     * @return The Graph totally backed up
     * @throws InterruptedException
     */
    public Graph backup() throws InterruptedException{
        synchronized (LOCK) {
            _graph.connect(null);
            long initialBench = System.currentTimeMillis();

            loadFiles(_folderPath);

            ExecutorService es = Executors.newFixedThreadPool(4);
            // For each shard
            for (Integer shardKey : _fileMap.keySet()) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        ShardLoader loader = new ShardLoader(_fileMap.get(shardKey));
                        loader.run(_graph);
                    }
                });
            }

            es.shutdown();
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            System.out.println("Backup took: " + ((System.currentTimeMillis() - initialBench) / 1000) + " s");
            _graph.disconnect(null);

            return _graph;
        }
    }

    /**
     * Backup system on a given period
     * @param initialStamp Starting timestamp to backup
     * @param endStamp Ending timestamp to backup
     * @return The graph recovered
     * @throws InterruptedException
     */
    public Graph backupSequence(long initialStamp, long endStamp) throws InterruptedException {
        synchronized (LOCK) {
            _graph.connect(null);
            long initialBench = System.currentTimeMillis();

            loadFileFromSequence(_folderPath, initialStamp, endStamp);

            ExecutorService es = Executors.newFixedThreadPool(4);
            // For each shard
            for (Integer shardKey : _fileMap.keySet()) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        ShardLoader loader = new ShardLoader(_fileMap.get(shardKey));
                        loader.run(_graph);
                    }
                });
            }

            es.shutdown();
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            System.out.println("Backup took: " + ((System.currentTimeMillis() - initialBench) / 1000) + " s");
            _graph.disconnect(null);

            return _graph;
        }
    }

    /**
     * Backup one node from a given timelapse
     * @param initialStamp The starting lapse to backup from
     * @param endStamp The ending lapse to backup to
     * @param nodeId List of all the nodes to recover
     * @return The Graph with the node recovered on the given timelapse
     * @throws InterruptedException
     */
    public Graph backupNodeSequence(long initialStamp, long endStamp, List<Long> nodeId) throws InterruptedException{
        synchronized (LOCK) {
            _graph.connect(null);
            long initialBench = System.currentTimeMillis();

            loadFileFromSequence(_folderPath, initialStamp, endStamp);

            ExecutorService es = Executors.newFixedThreadPool(_poolSize);
            // For each shard
            for (Integer shardKey : _fileMap.keySet()) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        ShardLoader loader = new ShardLoader(_fileMap.get(shardKey), nodeId);
                        loader.run(_graph);
                    }
                });
            }
            es.shutdown();
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            System.out.println("Backup took: " + ((System.currentTimeMillis() - initialBench) / 1000) + " s");
            _graph.disconnect(null);
            return _graph;
        }
    }

    /**
     * Method to backup only one node on an existing graph
     * @param nodeId List of all the id of the nodes to recover
     * @return The graph edited with the backup for this node executed
     * @throws InterruptedException
     */
    public Graph nodeBackup(List<Long> nodeId) throws InterruptedException{
        synchronized (LOCK) {
            _graph.connect(null);
            long initialBench = System.currentTimeMillis();

            loadFiles(_folderPath);

            ExecutorService es = Executors.newFixedThreadPool(_poolSize);
            for (Integer shardKey : _fileMap.keySet()) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        ShardLoader loader = new ShardLoader(_fileMap.get(shardKey), nodeId);
                        loader.run(_graph);
                    }
                });
            }

            es.shutdown();
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            System.out.println("Backup took: " + ((System.currentTimeMillis() - initialBench) / 1000) + " s");
            _graph.disconnect(null);

            return _graph;
        }
    }

    /**
     * Loads the map of elements
     * @param basedir The basedir containing all the files of the backup
     */
    private void loadFiles(String basedir){
        loadFileFromSequence(basedir, 0, System.currentTimeMillis());
    }

    /**
     * Loads the map of elements for a given range of timestamp
     * @param basedir Base directory containing all the files
     * @param startSeq Timestamp we should start at
     * @param endSeq Timestamp we should end at
     */
    private void loadFileFromSequence(String basedir, long startSeq, long endSeq){
        Path basePath = Paths.get(basedir);

        List<String> files = new ArrayList<>();
        files = getFiles(files, basePath);

        for(String file : files){
            FileKey key = new FileKey();
            String midPath = file.substring(file.indexOf("/shard"), file.length());

            String numberMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.indexOf("/save"), midPath.indexOf("-")));
            String shardMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(0, midPath.indexOf("/timelapse")));
            String startLapseMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.indexOf("-"), midPath.lastIndexOf("-")));
            String endLapseMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.lastIndexOf("-"), midPath.length()));

            int shard = Integer.valueOf(shardMatch);
            long startLapse = Long.valueOf(startLapseMatch);
            long fileNumber = Long.valueOf(numberMatch);
            long endLapse = System.currentTimeMillis();

            if(!endLapseMatch.equals("")){
                endLapse = Long.valueOf(endLapseMatch);
            }

            key.setEndLapse(endLapse);
            key.setStartLapse(startLapse);
            key.setFilePath(file);

            if((startLapse >= startSeq && startLapse <= endSeq) || (endLapse >= startSeq && endLapse <= endSeq) ) {
                Map<Long, FileKey> initialMap = _fileMap.get(shard);
                if (initialMap == null) {
                    initialMap = new HashMap<>();
                }
                initialMap.put(fileNumber, key);
                _localFiles.add(file);
                _fileMap.put(shard, initialMap);
            }
        }

        loadExternalFiles(startSeq,endSeq);
    }

    /**
     * Adds missing files from the bucket
     */
    private void loadExternalFiles(long startSeq, long endSeq){
        try {
            MinioClient minioClient = new MinioClient(BackupOptions.minioPath(),
                    BackupOptions.accessKey(),
                    BackupOptions.secretKey());

            if (minioClient.bucketExists("logs")) {
                Iterable<Result<Item>> myObjects = minioClient.listObjects("logs");
                List<String> missingItems = new ArrayList<>();

                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    String fileName = item.objectName();

                    String midPath = fileName.substring(fileName.indexOf("/shard"), fileName.length());
                    String startLapseMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.indexOf("-"), midPath.lastIndexOf("-")));
                    String endLapseMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.lastIndexOf("-"), midPath.length()));
                    String numberMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.indexOf("/save"), midPath.indexOf("-")));
                    String shardMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(0, midPath.indexOf("/timelapse")));

                    int shard = Integer.valueOf(shardMatch);
                    long startLapse = Long.valueOf(startLapseMatch);
                    long fileNumber = Long.valueOf(numberMatch);
                    long endLapse = System.currentTimeMillis();

                    FileKey key = new FileKey();
                    key.setEndLapse(endLapse);
                    key.setStartLapse(startLapse);
                    key.setFilePath(fileName);

                    // If not in the local files and Corresponds to our part to backup
                    if(!_localFiles.contains(fileName) && ((startLapse >= startSeq && startLapse <= endSeq) || (endLapse >= startSeq && endLapse <= endSeq))) {
                        missingItems.add(fileName);
                        Map<Long, FileKey> initialMap = _fileMap.get(shard);
                        if (initialMap == null) {
                            initialMap = new HashMap<>();
                        }
                        initialMap.put(fileNumber, key);
                        _localFiles.add(fileName);
                        _fileMap.put(shard, initialMap);
                    }
                }

                for(String name : missingItems){
                    System.out.println("Downloading " + name);

                    File file = new File(name);
                    File parent = file.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException("Couldn't create dir: " + parent);
                    }

                    minioClient.getObject("logs", name, name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error occurred: " + e);
        }
    }

    /**
     * Get all files in directory and sub
     * @param fileNames List of file names that will be completed
     * @param dir The basedir
     * @return Completed list of files in this folder
     */
    private List<String> getFiles(List<String> fileNames, Path dir) {
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    getFiles(fileNames, path);
                } else {
                    if(path.toString().contains(".spl")){
                        fileNames.add(path.toAbsolutePath().toString());
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }
}
