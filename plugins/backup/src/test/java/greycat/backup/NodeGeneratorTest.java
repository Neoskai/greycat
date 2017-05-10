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

import greycat.*;
import greycat.plugin.Job;
import org.junit.Ignore;
import org.junit.Test;

public class NodeGeneratorTest {

    final int valuesToInsert= 1000000;
    final long initialStamp = 1000;

    @Ignore
    @Test
    public void test(){
        Graph graph = new GraphBuilder()
                .withMemorySize(2000000)
                .build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final long before = System.currentTimeMillis();
                System.out.println("Connected to graph");

                final Node initialNode = graph.newNode(0,0);
                final DeferCounter counter = graph.newCounter(valuesToInsert);

                for(long i = 0 ; i < valuesToInsert; i++){

                    if(i%(valuesToInsert/10) == 0) {
                        System.out.println("<insert til " + i + " in " + (System.currentTimeMillis() - before) / 1000 + "s");
                        graph.save(new Callback<Boolean>() {
                            @Override
                            public void on(Boolean result) {
                                // NOTHING
                            }
                        });
                    }

                    final double value= i * 0.3;
                    final long time = initialStamp + i;

                    graph.lookup(0, time, initialNode.id(), new Callback<Node>() {
                        @Override
                        public void on(Node timedNode) {
                            timedNode.set("value", Type.DOUBLE, value);
                            counter.count();
                            timedNode.free();
                        }
                    });
                }

                initialNode.free();

                counter.then(new Job() {
                    @Override
                    public void run() {
                        System.out.println("<end insert phase>" + " " + (System.currentTimeMillis() - before) / 1000 + "s");
                        System.out.println( "Sparkey result: " + (valuesToInsert / ((System.currentTimeMillis() - before) / 1000) / 1000) + "kv/s");


                        graph.disconnect(new Callback<Boolean>() {
                            @Override
                            public void on(Boolean result) {
                                System.out.println("Disconnected from graph");
                            }
                        });
                    }
                });

            }
        });
    }


    @Test
    public void testNodeCreation(){
        Graph graph = new GraphBuilder()
                .withMemorySize(2000000)
                .build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final long before = System.currentTimeMillis();
                System.out.println("Connected to graph");

                final DeferCounter counter = graph.newCounter(valuesToInsert);

                for(long i = 0 ; i < valuesToInsert; i++){
                    Node initialNode = graph.newNode(0,0);

                    final double value= i * 0.3;
                    final long time = initialStamp + i;

                    graph.lookup(0, time, initialNode.id(), new Callback<Node>() {
                        @Override
                        public void on(Node timedNode) {
                            timedNode.set("value", Type.DOUBLE, value);
                            counter.count();
                            timedNode.free();
                        }
                    });


                    if(i%(valuesToInsert/10) == 0) {
                        System.out.println("<insert til " + i + " in " + (System.currentTimeMillis() - before) / 1000 + "s");
                        graph.save(new Callback<Boolean>() {
                            @Override
                            public void on(Boolean result) {
                                // NOTHING
                            }
                        });
                    }

                    initialNode.free();
                }


                counter.then(new Job() {
                    @Override
                    public void run() {
                        System.out.println("<end insert phase>" + " " + (System.currentTimeMillis() - before) / 1000 + "s");
                        System.out.println( "Sparkey result: " + (valuesToInsert / ((System.currentTimeMillis() - before) / 1000) / 1000) + "kv/s");


                        graph.disconnect(new Callback<Boolean>() {
                            @Override
                            public void on(Boolean result) {
                                System.out.println("Disconnected from graph");
                            }
                        });
                    }
                });

            }
        });
    }


}
