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
package greycat.backup.sample;

import greycat.*;

public class GenerationSample
{
    public static void main(String[] args) {
        final int valuesToInsert= 100000;
        final long initialStamp = 1000;

        Graph graph = new GraphBuilder()
                .withMemorySize(2000000)
                .build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final long before = System.currentTimeMillis();

                for(long i = 0 ; i < valuesToInsert; i++){
                    Node initialNode = graph.newNode(0,0);

                    for(long j = 0 ; j < valuesToInsert; j++){
                        if(j%(valuesToInsert/10) == 0) {
                            graph.save(new Callback<Boolean>() {
                                @Override
                                public void on(Boolean result) {
                                    // NOTHING
                                }
                            });
                        }

                        final double value= j * 0.3;
                        final long time = initialStamp + j;

                        graph.lookup(0, time, initialNode.id(), new Callback<Node>() {
                            @Override
                            public void on(Node timedNode) {
                                timedNode.set("value", Type.DOUBLE, value);
                                timedNode.free();
                            }
                        });
                    }

                    initialNode.free();
                }

                System.out.println("Generation took: " + ((System.currentTimeMillis()  - before)/1000) + " s");
            }
        });
    }
}
