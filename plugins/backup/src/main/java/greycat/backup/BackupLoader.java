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
package greycat.backup;

import com.google.common.base.CharMatcher;
import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyLogIterator;
import com.spotify.sparkey.SparkeyReader;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.tools.FileKey;
import greycat.backup.tools.StartingPoint;
import greycat.backup.tools.StorageKeyChunk;
import greycat.rocksdb.RocksDBStorage;
import greycat.struct.Buffer;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BackupLoader {
    private static final int POOLSIZE = 5;
    private static final int THREADPOOL = 1000;

    private Graph _graph;
    private String _folderPath;

    private HashMap<Long, StartingPoint> _nodes;
    private Map<Integer, Map<Long, FileKey>> _fileMap;

    private List<String> _fileList;

    public BackupLoader(String folderPath){
        this(folderPath,
                new GraphBuilder()
                .withStorage(new RocksDBStorage("data"))
                .withMemorySize(100000)
                .build());
    }

    public BackupLoader(String folderPath, Graph graphToUse){
        _folderPath = folderPath;
        _graph = graphToUse;

        _fileMap = new HashMap<>();
        _nodes = new HashMap<>();
        _fileList = new ArrayList<>();
    }

    /**
     * Launches the backup of the graph
     * @return The Graph resulting of the execution of the backup process
     * @throws InterruptedException Error if something happened during backup
     */
    public Graph backup() throws InterruptedException {
        _graph.connect(null);
        long initialBench = System.currentTimeMillis();

        loadFiles(_folderPath);
        loadNodes();

        ExecutorService es = Executors.newFixedThreadPool(THREADPOOL);
        for (Long id: _nodes.keySet()){
            int currentPool = Math.toIntExact(id%POOLSIZE);
            if( _fileMap.get(currentPool) == null || _nodes.get(id) == null){
                continue;
            }
            es.execute(new Runnable() {
                @Override
                public void run() {
                    NodeLoader loader = new NodeLoader(id, _nodes.get(id), _fileMap.get(Math.toIntExact(id%POOLSIZE)));
                    loader.run(_graph);
                    _graph.save(null);
                }
            });
        }
        es.shutdown();
        es.awaitTermination(10, TimeUnit.MINUTES);

        System.out.println("Backup took: " + ((System.currentTimeMillis()-initialBench)/1000) + " s");

        _graph.disconnect(null);

        return _graph;
    }

    /**
     * Backup system on a given period
     * @param initialStamp Starting timestamp to backup
     * @param endStamp Ending timestamp to backup
     * @return The graph recovered
     * @throws InterruptedException
     */
    public Graph backupSequence(long initialStamp, long endStamp) throws InterruptedException {
        _graph.connect(null);

        long initialBench = System.currentTimeMillis();
        loadFileFromSequence(_folderPath, initialStamp, endStamp);
        loadNodes();

        ExecutorService es = Executors.newFixedThreadPool(THREADPOOL);
        for (Long id: _nodes.keySet()){
            int currentPool = Math.toIntExact(id%POOLSIZE);

            if(_fileMap.get(currentPool) == null || _nodes.get(id) == null){
                continue;
            }
            es.execute(new Runnable() {
                @Override
                public void run() {
                    NodeLoader loader = new NodeLoader(id, _nodes.get(id), _fileMap.get(currentPool));
                    loader.run(_graph);
                }
            });
        }
        es.shutdown();
        es.awaitTermination(10, TimeUnit.MINUTES);

        System.out.println("Backup took: " + ((System.currentTimeMillis()-initialBench)/1000) + " s");

        _graph.disconnect(null);
        return _graph;
    }

    /**
     * Backup one node from a given timelapse
     * @param initialStamp The starting lapse to backup from
     * @param endStamp The ending lapse to backup to
     * @param nodeId The node to backup
     * @return The Graph with the node recovered on the given timelapse
     * @throws InterruptedException
     */
    public Graph backupNodeSequence(long initialStamp, long endStamp, long nodeId) throws InterruptedException{
        _graph.connect(null);

        long initialBench = System.currentTimeMillis();
        loadFileFromSequence(_folderPath, initialStamp, endStamp);
        loadNodes();

        int currentPool = Math.toIntExact(nodeId%POOLSIZE);
        if(! (_fileMap.get(currentPool) == null || _nodes.get(nodeId) == null) ){
            ExecutorService es = Executors.newFixedThreadPool(THREADPOOL);
            es.execute(new Runnable() {
                @Override
                public void run() {
                    NodeLoader loader = new NodeLoader(nodeId, _nodes.get(nodeId), _fileMap.get(Math.toIntExact(nodeId%POOLSIZE)));
                    loader.run(_graph);
                }
            });
            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
        }

        System.out.println("Backup took: " + ((System.currentTimeMillis()-initialBench)/1000) + " s");

        _graph.disconnect(null);
        return _graph;
    }

    /**
     * Method to backup only one node on an existing graph
     * @param nodeId Id of the node to recover
     * @return The graph edited with the backup for this node executed
     * @throws InterruptedException
     */
    public Graph nodeBackup(long nodeId) throws InterruptedException{
        _graph.connect(null);

        long initialBench = System.currentTimeMillis();
        loadFiles(_folderPath);
        loadNodes();

        int currentPool = Math.toIntExact(nodeId%POOLSIZE);
        if(_fileMap.get(currentPool) == null || _nodes.get(nodeId) == null){
            return _graph;
        }

        ExecutorService es = Executors.newFixedThreadPool(THREADPOOL);
        es.execute(new Runnable() {
            @Override
            public void run() {
                NodeLoader loader = new NodeLoader(nodeId, _nodes.get(nodeId), _fileMap.get(Math.toIntExact(nodeId%POOLSIZE)));
                loader.run(_graph);
            }
        });
        es.shutdown();
        es.awaitTermination(10, TimeUnit.MINUTES);

        System.out.println("Backup took: " + ((System.currentTimeMillis()-initialBench)/1000) + " s");
        _graph.disconnect(null);

        return _graph;
    }

    /**
     * Function that load the _nodes and their first occurence in the process
     */
    private void loadNodes(){
        for (String file : _fileList) {
            try {
                File logFile = new File(file);
                SparkeyLogIterator logIterator = new SparkeyLogIterator(Sparkey.getLogFile(logFile));

                for (SparkeyReader.Entry entry : logIterator) {
                    if (entry.getType() == SparkeyReader.Type.PUT) {
                        Buffer buffer = _graph.newBuffer();
                        buffer.writeAll(entry.getKey());

                        StorageKeyChunk key = StorageKeyChunk.buildFromString(buffer);

                        String midPath = file.substring(file.indexOf("/shard"), file.length());
                        String numberMatch = CharMatcher.inRange('0', '9').retainFrom(midPath.substring(midPath.indexOf("/save"), midPath.indexOf("-")));
                        long fileNumber = Long.valueOf(numberMatch);

                        if(!_nodes.keySet().contains(key.id())){
                            StartingPoint start = new StartingPoint(fileNumber, key.eventId());
                            _nodes.put(key.id(), start);
                        }

                        buffer.free();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                _fileMap.put(shard, initialMap);
                _fileList.add(file);
            }

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
