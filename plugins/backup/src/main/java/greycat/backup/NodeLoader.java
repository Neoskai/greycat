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
import greycat.backup.tools.StorageValueChunk;
import greycat.struct.Buffer;

import java.io.File;

public class NodeLoader extends Thread{

    private long _totalEvents;
    private String _folderPath;
    private long _nodeId;
    private SparkeyReader _reader;

    private String _currentFile;
    private long _newNodeId;

    public NodeLoader(long node, String folderPath, long totalEvents){
        _nodeId = node;
        _folderPath = folderPath;
        _totalEvents = totalEvents;
        _currentFile = "";
        _newNodeId = 0;
    }

    public void openReader(String filepath){
        try {
            File backupFile = new File(filepath);

            System.out.println("Looking for: " + filepath);
            if (!backupFile.exists()) {
                System.err.println("File does not exist");
            }

            _reader = Sparkey.open(backupFile);
            _currentFile = filepath;
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void run(Graph g) {
        System.out.println("Launching backup for node");

        openReader(findHolder(0));
        for(int i= 0; i < _totalEvents; i++){
            // Opening the holder of the given id if not loaded
            if(!_currentFile.equals(findHolder(i))){
                openReader(findHolder(i));
            }
            try {
                String currentKey = _nodeId + ";" + i;
                byte[] valueBytes =_reader.getAsByteArray(currentKey.getBytes());

                Buffer buffer = g.newBuffer();
                buffer.writeAll(valueBytes);
                StorageValueChunk value = StorageValueChunk.build(buffer);
                buffer.free();

                if(i== 0){
                    Node newNode = g.newNode(value.world(), value.time());
                    newNode.set(value.index(), value.type(), value.value());
                    _newNodeId = newNode.id();
                } else {
                    // If this node was already created, we lookup for it and write the value
                    g.lookup(value.world(), value.time(), _newNodeId, new Callback<Node>() {
                        @Override
                        public void on(Node result) {
                            if(value.type() == Type.REMOVE){
                                result.remove(value.index());
                            }else {
                                //System.out.println("Current system: " + key.index() + " " + key.world() + " " +  key.time() + " " + value.type() + " " + value.value());
                                result.set(value.index(), value.type(), value.value());
                            }
                        }
                    });
                }

            } catch (Exception e ){
                e.printStackTrace();
            }

        }
    }

    /**
     * Check the file holding the given event id for the current node
     * @param eventId The concerned eventID
     * @return The filepath of the file containing this node/event
     */
    public String findHolder(long eventId){
        int fileNumber = (int) eventId/10;
        return _folderPath + "/save_" + _nodeId + "_" + fileNumber+".spl";
    }
}
