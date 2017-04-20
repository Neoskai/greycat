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

package greycat.aerospike;

import com.aerospike.client.*;

import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import greycat.*;
import greycat.plugin.Job;
import greycat.scheduler.NoopScheduler;
import org.junit.Test;

public class StorageTest {

    final int valuesToInsert= 1000;

    @Test
    public void test(){
        Graph graph = new GraphBuilder().withStorage(new AerospikeDBStorage("localhost",3000, "test")).withScheduler(new NoopScheduler()).withMemorySize(2000000).build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                System.out.println("Connected to graph");

                final Node initialNode = graph.newNode(0,0);
                final DeferCounter counter = graph.newCounter(valuesToInsert);

                final long initialStamp = 1000;

                for(long i = 0 ; i < valuesToInsert; i++){

                    if(i%(valuesToInsert/10) == 0) {
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

        //clean();
    }

    static int count = 0;

    private static void clean(){
        try {
            final AerospikeClient client = new AerospikeClient("localhost", 3000);

            ScanPolicy scanPolicy = new ScanPolicy();
            client.scanAll(scanPolicy, "test", "greycat", new ScanCallback() {

                public void scanCallback(Key key, Record record) throws AerospikeException {

                    client.delete(new WritePolicy(), key);
                    count++;

                    if (count % 25000 == 0){
                        System.out.println("Deleted: " + count);
                    }
                }
            }, new String[] {});
            System.out.println("Deleted " + count + " from greycat");
        } catch (AerospikeException e) {
            e.printStackTrace();
        }
    }
}
