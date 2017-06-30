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

import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyLogIterator;
import com.spotify.sparkey.SparkeyReader;
import greycat.*;
import greycat.backup.tools.FileKey;
import greycat.backup.tools.StorageKeyChunk;
import greycat.backup.tools.StorageValueChunk;
import greycat.base.BaseNode;
import greycat.struct.Buffer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShardLoader extends Thread{
    private Map<Long,FileKey> _fileMap;

    private List<Long> _nodeFilter; // Contains all the nodes to backup. If empty or null, backup all nodes
    private Map<Long, Long> _backupStatus; // For each node that we are backing up, contains the last event backed up

    private int _savePoint;

    public ShardLoader(Map<Long,FileKey> fileMap){
        this(fileMap, null);
    }

    public ShardLoader(Map<Long,FileKey> fileMap, List<Long> nodeFilter){
        _fileMap = fileMap;
        _nodeFilter = nodeFilter;
        _backupStatus = new HashMap<>();

        _savePoint = BackupOptions.savePoint();
    }

    public void run(Graph g){
        Buffer buffer = g.newBuffer();

        for(Long key :_fileMap.keySet()){
            //For each file that we need to backup
            String file = _fileMap.get(key).getFilePath();

            try {
                File logFile = new File(file);
                SparkeyLogIterator logIterator = new SparkeyLogIterator(Sparkey.getLogFile(logFile));

                for (SparkeyReader.Entry entry : logIterator) {
                    if (entry.getType() == SparkeyReader.Type.PUT) {
                        buffer.writeAll(entry.getKey());
                        StorageKeyChunk storageKey = StorageKeyChunk.buildFromString(buffer);

                        buffer.free();
                        buffer.writeAll(entry.getValue());
                        StorageValueChunk value = StorageValueChunk.build(buffer);

                        if(isToBackup(storageKey.id()) ){
                            _backupStatus.computeIfAbsent(storageKey.id(), k -> storageKey.eventId());

                            if( _backupStatus.get(storageKey.id()) != storageKey.eventId()){
                                System.out.println("Backing up event not in the right order. Expected: " + _backupStatus.get(storageKey.id()) + " received: " + storageKey.eventId());
                            }

                            // Backup the entry
                            g.lookup(value.world(), value.time(), storageKey.id(), new Callback<Node>() {
                                @Override
                                public void on(Node result) {
                                    if(result == null) {
                                        Node newNode = new BaseNode(value.world(), value.time(), storageKey.id(), g);
                                        g.resolver().initNode(newNode, Constants.NULL_LONG);

                                        if (value.type() == Type.REMOVE) {
                                            System.err.println("Can't remove attribute on newly created node. Passing...");
                                        }else {
                                            newNode.setAt(value.index(), value.type(), value.value());
                                        }
                                        newNode.free();
                                    }
                                    else {
                                        if (value.type() == Type.REMOVE) {
                                            result.removeAt(value.index());
                                        } else {
                                            result.setAt(value.index(), value.type(), value.value());
                                        }
                                        result.free();
                                    }
                                }
                            });

                            if(storageKey.eventId() % _savePoint== 0){
                                g.save(null);
                            }
                            _backupStatus.put(storageKey.id(), (storageKey.eventId()+1)); // Increasing the current expected event by 1
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
     * Check if we need to backup this node or not
     * @param nodeId The id of the node we want to test the condition on
     * @return True if needs to be backed up, false otherwise
     */
    private boolean isToBackup(Long nodeId){
        return _nodeFilter == null || _nodeFilter.size() == 0 || _nodeFilter.contains(nodeId);
    }
}
