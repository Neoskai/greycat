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

import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyReader;
import greycat.Callback;
import greycat.Graph;
import greycat.Node;
import greycat.Type;
import greycat.backup.tools.FileKey;
import greycat.backup.tools.StorageValueChunk;
import greycat.struct.Buffer;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class NodeLoader extends Thread{

    private static final int SAVEPOINT = 10000;

    private long _nodeId;
    private SparkeyReader _reader;

    private String _currentFile;
    private Long _currentNumber;
    private Map<Long, FileKey> _fileMap;

    private long _newNodeId;


    public NodeLoader(long node, Long firstFile, Map<Long, FileKey> submap){
        _nodeId = node;
        _newNodeId = 0;
        _currentNumber = firstFile;
        _currentFile = submap.get(firstFile).getFilePath();
        _fileMap = submap;
    }

    /**
     * Opens the reader for backup for the given file
     * @param filepath Path of the news file to read
     */
    private void openReader(String filepath){
        try {
            File backupFile = new File(filepath);

            if (!backupFile.exists()) {
                System.err.println("File does not exist");
            }

            _reader = Sparkey.open(backupFile);

            _currentNumber++;
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Runs the backup process for the current node on the given graph
     * @param g The graph to apply the backup on
     */
    public void run(Graph g) {
        openReader(_currentFile);
        Buffer buffer = g.newBuffer();

        boolean backupEnded = false;
        int eventId = 0;

        while(!backupEnded){
            try {
                if(eventId%SAVEPOINT == 0){
                    g.save(null);
                }

                String currentKey = _nodeId + ";" + eventId;
                byte[] valueBytes =_reader.getAsByteArray(currentKey.getBytes());

                buffer.writeAll(valueBytes);
                StorageValueChunk value = StorageValueChunk.build(buffer);
                buffer.free();

                if(eventId== 0){
                    Node newNode = g.newNode(value.world(), value.time());
                    newNode.setAt(value.index(), value.type(), value.value());
                    _newNodeId = newNode.id();

                } else {
                    g.lookup(value.world(), value.time(), _newNodeId, new Callback<Node>() {
                        @Override
                        public void on(Node result) {
                            if(value.type() == Type.REMOVE){
                                result.removeAt(value.index());
                            }else {
                                //System.out.println("Current system: " + value.index() + " " + value.type() + " " + value.value());
                                result.setAt(value.index(), value.type(), value.value());
                            }
                            result.free();
                        }
                    });
                }

                eventId++;

                String upcomingKey = _nodeId + ";" + eventId;

                while(_fileMap.keySet().contains(_currentNumber)){
                    if(_reader.getAsByteArray(upcomingKey.getBytes()) == null) {
                        _reader.close();
                        openReader(nextHolder());
                    }
                    else{
                        break;
                    }
                }

                if(!_fileMap.keySet().contains(_currentNumber)){
                    backupEnded = true;
                }

            } catch (IOException | NullPointerException e ){
                e.printStackTrace();
            }
        }
    }

    /**
     * Check for the next file in the list
     * @return The filepath of the file containing this node/event
     */
    private String nextHolder(){
        return _fileMap.get(_currentNumber).getFilePath();
    }

}
