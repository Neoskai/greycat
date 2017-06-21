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
import greycat.struct.Relation;

public class WorldSample {

    public static void main(String[] args) {
        Graph g = GraphBuilder.newBuilder().build();
        test(g);
    }

    public static void test(final Graph graph) {
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean o) {
                final Node node0 = graph.newNode(0, 0);

                //do something selectWith the node
                graph.lookup(0, 0, node0.id(), new Callback<Node>() {
                    @Override
                    public void on(Node result) {
                        //check that the lookup return the same
                        result.free();

                        node0.set("name", Type.STRING, "MyName");
                        node0.remove("name");
                        node0.set("name", Type.STRING, "MyName");
                        node0.set("value", Type.STRING, "MyValue");
                        node0.set("name", Type.STRING, "MyName2");

                        node0.travelInTime(10, new Callback<Node>() {
                            @Override
                            public void on(Node result) {
                                result.set("value", Type.STRING, "Value10Time");
                                result.set("time", Type.INT, 10);
                            }
                        });

                        node0.travelInTime(0, new Callback<Node>() {
                            @Override
                            public void on(Node result) {
                                // Nothing
                            }
                        });

                        //Create a new node
                        Node node1 = graph.newNode(0, 0);

                        //attach the new node
                        node1.addToRelation("children", node0);
                        node1.addToRelation("children", node0);

                        Node node2 = graph.newNode(0, 0);
                        node1.addToRelation("children", node2);
                    }
                });

            }
        });

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                graph.lookup(0, 0, 1, new Callback<Node>() {
                    @Override
                    public void on(Node result) {
                        System.out.println("Node found: ");
                        System.out.println(result.toString());
                    }
                });

                graph.lookup(0, 10, 1, new Callback<Node>() {
                    @Override
                    public void on(Node result) {
                        System.out.println("Node found: ");
                        System.out.println(result.toString());
                    }
                });

                graph.disconnect(new Callback<Boolean>() {
                    @Override
                    public void on(Boolean result) {
                        // Nothing
                    }
                });
            }
        });

    }
}
