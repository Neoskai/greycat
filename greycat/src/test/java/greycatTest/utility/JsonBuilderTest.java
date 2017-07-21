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

package greycatTest.utility;

import greycat.*;
import greycat.plugin.Job;
import greycat.struct.*;
import greycat.utility.HashHelper;
import greycat.utility.JsonBuilder;
import org.junit.Assert;
import org.junit.Test;

import static greycat.Tasks.newTask;
import static greycat.internal.task.CoreActions.createNode;
import static greycat.internal.task.CoreActions.setAttribute;
import static org.junit.Assert.assertEquals;

/**
 * @ignore ts
 */
public class JsonBuilderTest {

    @Test
    public void testBuilder(){
        String boolJson = JsonBuilder.buildJson(Type.BOOL, true);
        String boolWriting = "[1,true]";
        assertEquals(boolJson, boolWriting);

        String stringJson = JsonBuilder.buildJson(Type.STRING, "hello");
        String stringWriting = "[2,\"hello\"]";
        assertEquals(stringJson, stringWriting);

        String longJson =JsonBuilder.buildJson(Type.LONG, 1712771606L);
        String longWriting = "[3, 1712771606]";
        assertEquals(longJson, longWriting);

        String intJson =JsonBuilder.buildJson(Type.INT, -70308288);
        String intWriting = "[4,-70308288]";
        assertEquals(intJson, intWriting);

        String doubleJson =JsonBuilder.buildJson(Type.DOUBLE, 3973226699.47893);
        String doubleWriting = "[5,3.97322669947893E9]";
        assertEquals(doubleJson, doubleWriting);

        Graph g = new GraphBuilder().build();
        g.connect(null);
        Node n = g.newNode(0,0);

        DoubleArray dArray = (DoubleArray) n.getOrCreate("DArray", Type.DOUBLE_ARRAY);
        double[] doArray = {0.4, 0.6, 10.89, -14986.78};
        dArray.addAll(doArray);
        String darrayJson = JsonBuilder.buildJson(Type.DOUBLE_ARRAY, dArray);
        String dArrayWriting ="[6,[0.4,0.6,10.89,-14986.78]]";
        assertEquals(dArrayWriting,darrayJson);

        LongArray lArray = (LongArray) n.getOrCreate("LArray", Type.LONG_ARRAY);
        long[] loArray = {888888888, 888884136, 16468489, -544844444, -156};
        lArray.addAll(loArray);
        String larrayJson = JsonBuilder.buildJson(Type.LONG_ARRAY, lArray);
        String lArrayWriting = "[7,[888888888,888884136,16468489,-544844444,-156]]";
        assertEquals(larrayJson, lArrayWriting);

        IntArray iArray = (IntArray) n.getOrCreate("IArray", Type.INT_ARRAY);
        int[] ioArray = {8,42,98,-985,-51,0};
        iArray.addAll(ioArray);
        String iArrayJson = JsonBuilder.buildJson(Type.INT_ARRAY, iArray);
        String iArrayWriting = "[8,[8,42,98,-985,-51,0]]";
        assertEquals(iArrayJson,iArrayWriting);

        StringArray sArray = (StringArray) n.getOrCreate("SArray", Type.STRING_ARRAY);
        String[] soArray = {"Hello","Goodbye","azerty!","123456_)zer", "back\\nline"};
        sArray.addAll(soArray);
        String sArrayJson = JsonBuilder.buildJson(Type.STRING_ARRAY, sArray);
        String sArrayWriting = "[9,[\"Hello\",\"Goodbye\",\"azerty!\",\"123456_)zer\",\"back\\nline\"]]";
        assertEquals(sArrayJson,sArrayWriting);

        LongLongMap llMap = (LongLongMap) n.getOrCreate("LLmap", Type.LONG_TO_LONG_MAP);
        for(int i =0; i <5; i++){
            llMap.put(i,i*10);
        }
        String llMapJson = JsonBuilder.buildJson(Type.LONG_TO_LONG_MAP, llMap);
        String llMapWriting = "[10,{\"0\":0,\"1\":10,\"2\":20,\"3\":30,\"4\":40}]";
        assertEquals(llMapJson,llMapWriting);

        LongLongArrayMap llaMap = (LongLongArrayMap) n.getOrCreate("LLAMap", Type.LONG_TO_LONG_ARRAY_MAP);
        for(int i = 0; i < 10; i++){
            llaMap.put(i%3, i);
        }
        String llaMapJson = JsonBuilder.buildJson(Type.LONG_TO_LONG_ARRAY_MAP, llaMap);
        String llaMapWriting = "[11,{\"0\":[9,6,3,0],\"1\":[7,4,1],\"2\":[8,5,2]}]";
        assertEquals(llaMapJson,llaMapWriting);

        StringIntMap siMap = (StringIntMap) n.getOrCreate("SIMap", Type.STRING_TO_INT_MAP);
        for(int i = 0; i<3; i++){
            siMap.put("index"+i, i);
        }
        String siMapJson = JsonBuilder.buildJson(Type.STRING_TO_INT_MAP, siMap);
        String siMapWriting = "[12,{\"index0\":0,\"index1\":1,\"index2\":2}]";
        assertEquals(siMapJson,siMapWriting);

        Node nRel1 = g.newNode(0,0);
        Node nRel2 = g.newNode(0,0);
        Node nRel3 = g.newNode(0,0);
        nRel1.addToRelation("TestRelation", nRel2);
        nRel1.addToRelation("TestRelation", nRel3);

        Relation r1 = nRel1.getRelation("TestRelation");
        String relJson = JsonBuilder.buildJson(Type.RELATION, r1);
        String relWriting ="[13,[3,4]]";
        assertEquals(relJson,relWriting);

        DMatrix dMatrix = (DMatrix) n.getOrCreate("DMatrix", Type.DMATRIX);
        dMatrix.init(5,4);
        for(int i= 0; i<5;i++){
            for(int j=0; j<4; j++){
                dMatrix.add(i,j, (i+j)/(double) 5);
            }
        }
        String dmatrixJson = JsonBuilder.buildJson(Type.DMATRIX, dMatrix);
        String dMatrixWriting = "[14,[5,4,0.0,0.2,0.4,0.6,0.2,0.4,0.6,0.8,0.4,0.6,0.8,1.0,0.6,0.8,1.0,1.2,0.8,1.0,1.2,1.4]]";
        assertEquals(dmatrixJson, dMatrixWriting);

        LMatrix lMatrix = (LMatrix) n.getOrCreate("LMatrix", Type.LMATRIX);
        lMatrix.init(4,5);
        for(int i= 0; i<4;i++){
            for(int j=0; j<5; j++){
                lMatrix.add(i,j, (i+1)*(j+1));
            }
        }
        String lMatrixJson= JsonBuilder.buildJson(Type.LMATRIX,lMatrix);
        String lMatrixWriting = "[15,[4,5,1,2,3,4,5,2,4,6,8,10,3,6,9,12,15,4,8,12,16,20]]";
        assertEquals(lMatrixJson, lMatrixWriting);

        // @TODO ESTRUCT - ESTRUCTARRAY - ERELATION

        Task task = newTask().loopPar("1", "2",
                newTask()
                        .declareIndex("rooms","name")
                        .createNode()
                        .setAttribute("name", Type.STRING, "room_{{i}}")
                        .updateIndex("rooms")
                        .defineAsVar("parentRoom")
                        .loop("1", "3",
                                newTask()
                                        .then(createNode())
                                        .then(setAttribute("sensor", Type.STRING, "sensor_{{i}}"))
                        )
        );

        String taskJson = JsonBuilder.buildJson(Type.TASK, task);
        String taskWriting = "[19,\"loopPar('1','2',{declareIndex('rooms','name').createNode().setAttribute('name',STRING,'room_{{i}}').updateIndex('rooms').defineAsVar('parentRoom').loop('1','3',{createNode().setAttribute('sensor',STRING,'sensor_{{i}}')})})\"]";
        assertEquals(taskJson,taskWriting);

        // @TODO TASKARRAY -  KDTREE - NDTREE

        g.declareIndex(0,"TestIndex", null);
        g.index(0,0,"TestIndex", index ->{
            index.update(nRel2);
            index.update(nRel1);
            index.update(nRel3);

            String indexJson = JsonBuilder.buildJson(Type.INDEX, index);
            String indexWriting = "[69808306,[21,{\"0\":[69808306,[16,[[{\"0\":[11,{\"-8753978149589990\":[4,2,3]}]},{\"1\":[10,{\"3\":-8753978149589990,\"2\":-8753978149589990,\"4\":-8753978149589990}]},{\"2\":[8,[]]}]]]]}]]";
            assertEquals(indexJson,indexWriting);
        });

        IntIntMap castedIIMap = (IntIntMap) n.getOrCreate("IIMap", Type.INT_TO_INT_MAP);
        for(int i = 0; i<5; i++){
            castedIIMap.put(i, i*i);
        }
        String iimapJSon = JsonBuilder.buildJson(Type.INT_TO_INT_MAP, castedIIMap);
        String iiMapWriting = "[22,{\"0\":0,\"1\":1,\"2\":4,\"3\":9,\"4\":16}]";
        assertEquals(iimapJSon, iiMapWriting);

        IntStringMap castedISMap = (IntStringMap) n.getOrCreate("ISMap", Type.INT_TO_STRING_MAP);
        for(int i = 0; i<5; i++){
            castedISMap.put(i, "index"+ i*i);
        }
        String isMapJson = JsonBuilder.buildJson(Type.INT_TO_STRING_MAP, castedISMap);
        String isMapWriting = "[23,{\"0\":\"index0\",\"1\":\"index1\",\"2\":\"index4\",\"3\":\"index9\",\"4\":\"index16\"}]";
        assertEquals(isMapJson,isMapWriting);

        String nodeJSon = JsonBuilder.buildJson(Type.NODE, n);
        String nodeWriting = "[21,{\"DArray\":[6,[0.4,0.6,10.89,-14986.78]],\"LArray\":[7,[888888888,888884136,16468489,-544844444,-156]],\"IArray\":[8,[8,42,98,-985,-51,0]],\"SArray\":[9,[\"Hello\",\"Goodbye\",\"azerty!\",\"123456_)zer\",\"back\\nline\"]],\"LLmap\":[10,{\"0\":0,\"1\":10,\"2\":20,\"3\":30,\"4\":40}],\"LLAMap\":[11,{\"0\":[9,6,3,0],\"1\":[7,4,1],\"2\":[8,5,2]}],\"SIMap\":[12,{\"index0\":0,\"index1\":1,\"index2\":2}],\"DMatrix\":[14,[5,4,0.0,0.2,0.4,0.6,0.2,0.4,0.6,0.8,0.4,0.6,0.8,1.0,0.6,0.8,1.0,1.2,0.8,1.0,1.2,1.4]],\"LMatrix\":[15,[4,5,1,2,3,4,5,2,4,6,8,10,3,6,9,12,15,4,8,12,16,20]],\"IIMap\":[22,{\"0\":0,\"1\":1,\"2\":4,\"3\":9,\"4\":16}],\"ISMap\":[23,{\"0\":\"index0\",\"1\":\"index1\",\"2\":\"index4\",\"3\":\"index9\",\"4\":\"index16\"}]}]";
        assertEquals(nodeJSon,nodeWriting);

        g.disconnect(null);

    }

    @Test
    public void testDMatrix() {
        Graph graph = GraphBuilder
                .newBuilder()
                .build();
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final Node node = graph.newNode(0, 0);
                final DMatrix matrix = (DMatrix) node.getOrCreate("matrix", Type.DMATRIX);
                matrix.init(3, 3);
                matrix.set(0, 0, 0);
                matrix.set(1, 1, 1);
                matrix.set(2, 2, 2);

                Assert.assertTrue(matrix.get(0,0)==0);
                Assert.assertTrue(matrix.get(1,1)==1);
                Assert.assertTrue(matrix.get(2,2)==2);
                node.travelInTime(1, new Callback<Node>() {
                    @Override
                    public void on(final Node node_t1) {
                        final DMatrix matrix_t1 = (DMatrix) node_t1.getOrCreate("matrix", Type.DMATRIX);
                        Assert.assertTrue(matrix_t1.get(0,0)==0);
                        Assert.assertTrue(matrix_t1.get(1,1)==1);
                        Assert.assertTrue(matrix_t1.get(2,2)==2);

                        matrix_t1.set(0, 0, 10);
                        matrix_t1.set(1, 1, 11);
                        matrix_t1.set(2, 2, 12);
                        graph.save(new Callback<Boolean>() {
                            @Override
                            public void on(Boolean saved) {

                                node_t1.travelInTime(0, new Callback<Node>() {
                                    @Override
                                    public void on(Node node2) {
                                        DMatrix matrix_t2 = (DMatrix) node2.getOrCreate("matrix", Type.DMATRIX);

                                        Assert.assertTrue(matrix_t2.get(0,0)==0);
                                        Assert.assertTrue(matrix_t2.get(1,1)==1);
                                        Assert.assertTrue(matrix_t2.get(2,2)==2);

                                        node2.travelInTime(1, new Callback<Node>() {
                                            @Override
                                            public void on(Node node3) {
                                                DMatrix matrix_t3 = (DMatrix) node3.getOrCreate("matrix", Type.DMATRIX);
                                                Assert.assertTrue(matrix_t3.get(0,0)==10);
                                                Assert.assertTrue(matrix_t3.get(1,1)==11);
                                                Assert.assertTrue(matrix_t3.get(2,2)==12);
                                            }
                                        });


                                    }
                                });

                            }
                        });

                        graph.declareIndex(0,"TestIndex", null);
                        graph.index(0,0,"TestIndex", index ->{
                            index.update(node);
                        });

                        String sToJson = graph.toJson();

                        Buffer buffer = graph.newBuffer();
                        graph.toJson(buffer);

                        assertEquals(sToJson, new String(buffer.data()));


                    }
                });

            }
        });
    }

    @Test
    public void testLMatrix() {
        Graph graph = GraphBuilder
                .newBuilder()
                .build();
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final Node node = graph.newNode(0, 0);
                final LMatrix matrix = (LMatrix) node.getOrCreate("matrix", Type.LMATRIX);
                matrix.init(3, 3);
                matrix.set(0, 0, 0);
                matrix.set(1, 1, 1);
                matrix.set(2, 2, 2);

                Assert.assertTrue(matrix.get(0,0)==0);
                Assert.assertTrue(matrix.get(1,1)==1);
                Assert.assertTrue(matrix.get(2,2)==2);
                node.travelInTime(1, new Callback<Node>() {
                    @Override
                    public void on(final Node node_t1) {
                        final LMatrix matrix_t1 = (LMatrix) node_t1.getOrCreate("matrix", Type.LMATRIX);
                        Assert.assertTrue(matrix_t1.get(0,0)==0);
                        Assert.assertTrue(matrix_t1.get(1,1)==1);
                        Assert.assertTrue(matrix_t1.get(2,2)==2);

                        matrix_t1.set(0, 0, 10);
                        matrix_t1.set(1, 1, 11);
                        matrix_t1.set(2, 2, 12);
                        graph.save(new Callback<Boolean>() {
                            @Override
                            public void on(Boolean saved) {

                                node_t1.travelInTime(0, new Callback<Node>() {
                                    @Override
                                    public void on(Node node2) {
                                        LMatrix matrix_t2 = (LMatrix) node2.getOrCreate("matrix", Type.LMATRIX);

                                        Assert.assertTrue(matrix_t2.get(0,0)==0);
                                        Assert.assertTrue(matrix_t2.get(1,1)==1);
                                        Assert.assertTrue(matrix_t2.get(2,2)==2);

                                        node2.travelInTime(1, new Callback<Node>() {
                                            @Override
                                            public void on(Node node3) {
                                                LMatrix matrix_t3 = (LMatrix) node3.getOrCreate("matrix", Type.LMATRIX);
                                                Assert.assertTrue(matrix_t3.get(0,0)==10);
                                                Assert.assertTrue(matrix_t3.get(1,1)==11);
                                                Assert.assertTrue(matrix_t3.get(2,2)==12);

                                            }
                                        });


                                    }
                                });

                            }
                        });

                        graph.declareIndex(0,"TestIndex", null);
                        graph.index(0,0,"TestIndex", index ->{
                            index.update(node);
                        });

                        String sToJson = graph.toJson();

                        Buffer buffer = graph.newBuffer();
                        graph.toJson(buffer);

                        assertEquals(sToJson, new String(buffer.data()));

                    }
                });

            }
        });
    }

    @Test
    public void helloWorldTest() {
        Graph graph = new GraphBuilder().build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean o) {

                final long available = graph.space().available();

                final Node node0 = graph.newNode(0, 0);

                //do something selectWith the node
                graph.lookup(0, 0, node0.id(), new Callback<Node>() {
                    @Override
                    public void on(Node result) {
                        //check that the lookup return the same
                        Assert.assertTrue(result.id() == node0.id());
                        result.free();

                        node0.set("name", Type.STRING, "MyName");
                        Assert.assertTrue(HashHelper.equals("MyName", node0.get("name").toString()));

                        node0.remove("name");
                        Assert.assertTrue(node0.get("name") == null);
                        node0.set("name", Type.STRING, "MyName");

                        node0.set("value", Type.STRING, "MyValue");
                        Assert.assertTrue(HashHelper.equals("MyValue", node0.get("value").toString()));
                        //check that other attribute name is not affected
                        Assert.assertTrue(HashHelper.equals("MyName", node0.get("name").toString()));

                        node0.set("name", Type.STRING, "MyName2");
                        Assert.assertTrue(HashHelper.equals("MyName2", node0.get("name").toString()));
                        Assert.assertTrue(HashHelper.equals("MyValue", node0.get("value").toString()));

                        //check the simple json print

                        String flatNode0 = "{\"world\":0,\"time\":0,\"id\":1,\"name\":\"MyName2\",\"value\":\"MyValue\"}";

                        Assert.assertTrue(flatNode0.length() == node0.toString().length());
                        Assert.assertTrue(HashHelper.equals("{\"world\":0,\"time\":0,\"id\":1,\"name\":\"MyName2\",\"value\":\"MyValue\"}", node0.toString()));

                        //Create a new node
                        Node node1 = graph.newNode(0, 0);
                        Assert.assertTrue(HashHelper.equals("{\"world\":0,\"time\":0,\"id\":2}", node1.toString()));

                        //attach the new node
                        node1.addToRelation("children", node0);
                        Assert.assertTrue(HashHelper.equals("{\"world\":0,\"time\":0,\"id\":2,\"children\":[1]}", node1.toString()));

                        node1.addToRelation("children", node0);
                        Assert.assertTrue(HashHelper.equals("{\"world\":0,\"time\":0,\"id\":2,\"children\":[1,1]}", node1.toString()));

                        Node node2 = graph.newNode(0, 0);
                        node1.addToRelation("children", node2);
                        Assert.assertTrue(HashHelper.equals("{\"world\":0,\"time\":0,\"id\":2,\"children\":[1,1,3]}", node1.toString()));

                        Relation refValuesThree = (Relation) node1.get("children");
                        Assert.assertTrue(refValuesThree.size() == 3);
                        Assert.assertTrue(refValuesThree.get(0) == 1);
                        Assert.assertTrue(refValuesThree.get(1) == 1);
                        Assert.assertTrue(refValuesThree.get(2) == 3);

                        graph.declareIndex(0, "TestIndex", null);
                        graph.index(0, 0, "TestIndex", index -> {
                            index.update(node0);
                            index.update(node1);
                            index.update(node2);
                        });

                        String sToJson = graph.toJson();

                        Buffer buffer = graph.newBuffer();
                        graph.toJson(buffer);

                        assertEquals(sToJson, new String(buffer.data()));

                    }
                });
            }
        });
    }

    @Test
    public void testEStructDMatrix() {
        Graph graph= GraphBuilder
                .newBuilder()
                .build();
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                Node node = graph.newNode(0,0);

                EStructArray eg= (EStructArray) node.getOrCreate("egraph", Type.ESTRUCT_ARRAY);
                EStruct en =eg.newEStruct();
                eg.setRoot(en);

                DMatrix matrix= (DMatrix)en.getOrCreate("matrix", Type.DMATRIX);

                matrix.init(3,3);
                matrix.set(0,0,0);
                matrix.set(1,1,1);
                matrix.set(2,2,2);
                Assert.assertTrue(matrix.get(0,0)==0);
                Assert.assertTrue(matrix.get(1,1)==1);
                Assert.assertTrue(matrix.get(2,2)==2);


                node.travelInTime(1, new Callback<Node>() {
                    @Override
                    public void on(Node result1) {
                        EStructArray eg= (EStructArray) result1.getOrCreate("egraph", Type.ESTRUCT_ARRAY);
                        EStruct en =eg.root();
                        DMatrix matrix_t1= (DMatrix)en.getOrCreate("matrix", Type.DMATRIX);
                        Assert.assertTrue(matrix_t1.get(0,0)==0);
                        Assert.assertTrue(matrix_t1.get(1,1)==1);
                        Assert.assertTrue(matrix_t1.get(2,2)==2);

                        matrix_t1.set(0,0,10);
                        matrix_t1.set(1,1,11);
                        matrix_t1.set(2,2,12);


                        result1.travelInTime(0, new Callback<Node>() {
                            @Override
                            public void on(Node result2) {
                                EStructArray eg= (EStructArray) result2.getOrCreate("egraph", Type.ESTRUCT_ARRAY);
                                EStruct en =eg.root();
                                DMatrix matrix_t2= (DMatrix)en.getOrCreate("matrix", Type.DMATRIX);
                                Assert.assertTrue(matrix_t2.get(0,0)==0);
                                Assert.assertTrue(matrix_t2.get(1,1)==1);
                                Assert.assertTrue(matrix_t2.get(2,2)==2);
                                result2.travelInTime(1, new Callback<Node>() {
                                    @Override
                                    public void on(Node result3) {
                                        EStructArray eg= (EStructArray) result3.getOrCreate("egraph", Type.ESTRUCT_ARRAY);
                                        EStruct en =eg.root();
                                        DMatrix matrix_t3= (DMatrix)en.getOrCreate("matrix", Type.DMATRIX);
                                        Assert.assertTrue(matrix_t3.get(0,0)==10);
                                        Assert.assertTrue(matrix_t3.get(1,1)==11);
                                        Assert.assertTrue(matrix_t3.get(2,2)==12);
                                    }
                                });
                            }
                        });


                    }
                });

                graph.declareIndex(0,"TestIndex", null);
                graph.index(0,0,"TestIndex", index ->{
                    index.update(node);
                });

                String sToJson = graph.toJson();

                Buffer buffer = graph.newBuffer();
                graph.toJson(buffer);

                assertEquals(sToJson, new String(buffer.data()));

            }
        });

    }

    final int valuesToInsert= 100;
    final long initialStamp = 1000;


    @Test
    public void testMultiNode(){
        // 5 Nodes with many points
        Graph graph = new GraphBuilder()
                .withMemorySize(2000000)
                .build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final long before = System.currentTimeMillis();
                System.out.println("Connected to graph");

                graph.declareIndex(0,"TestIndex", null);

                final DeferCounter counter = graph.newCounter(valuesToInsert);

                for(long i = 0 ; i < 5; i++){
                    Node initialNode = graph.newNode(0,0);

                    graph.index(0,0,"TestIndex", index ->{
                        index.update(initialNode);
                    });

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
                                graph.index(0,0,"TestIndex", index ->{
                                    index.update(timedNode);
                                });
                                counter.count();
                                timedNode.free();
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

        String sToJson = graph.toJson();

        Buffer buffer = graph.newBuffer();
        graph.toJson(buffer);

        assertEquals(sToJson, new String(buffer.data()));
    }

    @Test
    public void testMultiNodeSplitted(){
        // 5 Nodes with many points
        Graph graph = new GraphBuilder()
                .withMemorySize(2000000)
                .build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final long before = System.currentTimeMillis();
                System.out.println("Connected to graph");

                graph.declareIndex(0,"TestIndex", null);

                final DeferCounter counter = graph.newCounter(valuesToInsert);

                for(long i = 0 ; i < 5; i++){
                    Node initialNode = graph.newNode(0,0);

                    graph.index(0,0,"TestIndex", index ->{
                        index.update(initialNode);
                    });

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
                                graph.index(0,0,"TestIndex", index ->{
                                    index.update(timedNode);
                                });
                                counter.count();
                                timedNode.free();
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

        Buffer buffer = graph.newBuffer();
        graph.toJson(buffer, 1000L);
    }
}
