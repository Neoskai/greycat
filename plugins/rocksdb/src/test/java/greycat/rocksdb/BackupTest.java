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
package greycat.rocksdb;

import greycat.*;
import greycat.struct.BackupEntry;
import greycat.scheduler.NoopScheduler;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class BackupTest {

    @Test
    public void testCreation() throws IOException {
        Graph graph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage("db"))
                .build();

        final int valuesToInsert= 1000;
        final long initialStamp = 1000;

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                for(long i = 0 ; i < valuesToInsert; i++){
                    Node initialNode = graph.newNode(0,0);

                    final double value= i * 0.3;
                    final long time = initialStamp + i;

                    graph.lookup(0, time, initialNode.id(), new Callback<Node>() {
                        @Override
                        public void on(Node timedNode) {
                            timedNode.set("value", Type.DOUBLE, value);
                            timedNode.free();
                        }
                    });

                    if(i%(valuesToInsert/10) == 0) {
                        graph.save(null);
                    }

                    initialNode.free();
                }
            }
        });

        BackupEntry entry = graph.storage().createBackup();
        assertNotEquals(null, entry);

        graph.disconnect(null);

        File dbFile = new File("db/data");
        delete(dbFile);

    }

    @Test
    public void testBackup() throws IOException {
        Graph graph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage("db"))
                .build();

        final int valuesToInsert= 1000;
        final long initialStamp = 1000;

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                for(long i = 0 ; i < valuesToInsert; i++){
                    Node initialNode = graph.newNode(0,0);

                    final double value= i * 0.3;
                    final long time = initialStamp + i;

                    graph.lookup(0, time, initialNode.id(), new Callback<Node>() {
                        @Override
                        public void on(Node timedNode) {
                            timedNode.set("value", Type.DOUBLE, value);
                            timedNode.free();
                        }
                    });

                    if(i%(valuesToInsert/10) == 0) {
                        graph.save(null);
                    }

                    if(i%(valuesToInsert/5)==0) {
                        graph.storage().createBackup();
                    }

                    initialNode.free();
                }
            }
        });

        graph.disconnect(null);

        File dataFile = new File("db/data");
        delete(dataFile);

        Graph newGraph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage("db"))
                .build();

        newGraph.storage().loadBackup(2);

        newGraph.connect(null);

        newGraph.lookup(0, 0, 201, new Callback<Node>() {
            @Override
            public void on(Node result) {
                assertNotEquals(result, null);
            }
        });

        newGraph.disconnect(null);

        File completeFile = new File("db");
        delete(completeFile);
    }

    @Test
    public void testLastBackup() throws IOException {
        Graph graph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage("db"))
                .build();

        final int valuesToInsert= 1000;
        final long initialStamp = 1000;

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                for(long i = 0 ; i < valuesToInsert; i++){
                    Node initialNode = graph.newNode(0,0);

                    final double value= i * 0.3;
                    final long time = initialStamp + i;

                    graph.lookup(0, time, initialNode.id(), new Callback<Node>() {
                        @Override
                        public void on(Node timedNode) {
                            timedNode.set("value", Type.DOUBLE, value);
                            timedNode.free();
                        }
                    });

                    if(i%(valuesToInsert/10) == 0) {
                        graph.save(null);
                    }

                    if(i%(valuesToInsert/5)==0) {
                        graph.storage().createBackup();
                    }

                    initialNode.free();
                }
            }
        });

        graph.disconnect(null);

        File dbFile = new File("db/data");
        delete(dbFile);

        Graph newGraph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage("db"))
                .build();

        newGraph.storage().loadLatestBackup();

        newGraph.connect(null);

        newGraph.lookup(0, 0, 201, new Callback<Node>() {
            @Override
            public void on(Node result) {
                assertNotEquals(result, null);
            }
        });

        newGraph.disconnect(null);

        File completeFile = new File("db");
        delete(completeFile);
    }

    private static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
            } else {
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            //if file, then delete it
            file.delete();
        }
    }
}
