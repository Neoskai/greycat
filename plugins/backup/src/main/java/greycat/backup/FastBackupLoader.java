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
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.tools.FileKey;
import greycat.rocksdb.RocksDBStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static greycat.Constants.POOLSIZE;

public class FastBackupLoader {

    private Graph _graph;
    private String _folderPath;
    private Map<Integer, Map<Long, FileKey>> _fileMap;

    private Map<Long, Long> _startPoints;

    public FastBackupLoader(String folderPath){
        this(folderPath,
                new GraphBuilder()
                        .withStorage(new RocksDBStorage("data"))
                        .withMemorySize(100000)
                        .build());
    }

    public FastBackupLoader(String folderPath, Graph graphToUse){
        _folderPath = folderPath;
        _graph = graphToUse;

        _fileMap = new HashMap<>();
        _startPoints = new HashMap<>();
    }

    public Graph backup() throws InterruptedException{
        _graph.connect(null);
        long initialBench = System.currentTimeMillis();

        loadFiles(_folderPath);

        ExecutorService es = Executors.newFixedThreadPool(POOLSIZE);
        // For each shard
        for(Integer shardKey :_fileMap.keySet()){
            es.execute(new Runnable() {
                @Override
                public void run() {
                    ShardLoader loader = new ShardLoader(_fileMap.get(shardKey));
                    loader.run(_graph);
                }
            });
        }

        es.shutdown();
        es.awaitTermination(10, TimeUnit.HOURS);

        _graph.disconnect(null);

        return _graph;
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
