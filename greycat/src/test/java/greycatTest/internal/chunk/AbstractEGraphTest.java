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
package greycatTest.internal.chunk;

import greycat.Graph;
import greycat.chunk.ChunkType;
import greycat.chunk.StateChunk;
import greycat.plugin.MemoryFactory;
import greycat.plugin.Storage;
import greycat.struct.*;
import greycatTest.internal.MockStorage;
import org.junit.Assert;
import org.junit.Test;
import greycat.GraphBuilder;
import greycat.Type;
import greycat.chunk.ChunkSpace;
import greycat.scheduler.NoopScheduler;

public abstract class AbstractEGraphTest {

    private MemoryFactory factory;

    public AbstractEGraphTest(MemoryFactory factory) {
        this.factory = factory;
    }

    @Test
    public void simpleUsageTest() {
        Graph g = GraphBuilder.newBuilder().withScheduler(new NoopScheduler()).build();
        g.connect(null);
        ChunkSpace space = factory.newSpace(100,-1, g, false);
        StateChunk chunk = (StateChunk) space.createAndMark(ChunkType.STATE_CHUNK, 0, 0, 0);
        //test embedded graph attribute
        EGraph egraph = (EGraph) chunk.getOrCreateAt(0, Type.EGRAPH);
        //test primitive attribute
        ENode eNode = egraph.newNode();
        egraph.setRoot(eNode);
        eNode.set("name", Type.STRING, "hello");
        Assert.assertEquals("{\"name\":\"hello\"}", eNode.toString());
        //test single eRelation
        ENode secondENode = egraph.newNode();
        secondENode.set("name", Type.STRING, "secondNode");
        eNode.set("children", Type.ENODE, secondENode);
        ENode retrieved = (ENode) eNode.get("children");
        Assert.assertEquals(retrieved.toString(), retrieved.toString());
        //test eRelation
        ERelation eRelation = (ERelation) eNode.getOrCreate("testRel", Type.ERELATION);
        for (int i = 0; i < 3; i++) {
            ENode loopNode = egraph.newNode();
            loopNode.set("name", Type.STRING, "node_" + i);
            eRelation.add(loopNode);
        }
        ERelation resolvedERelation = (ERelation) eNode.get("testRel");
        Assert.assertEquals(3, resolvedERelation.size());
        Assert.assertEquals("[2,3,4]", resolvedERelation.toString());

        space.free(chunk);
        space.freeAll();
    }

    @Test
    public void setCostTest() {
        Graph g = GraphBuilder.newBuilder().withScheduler(new NoopScheduler()).build();
        g.connect(null);
        ChunkSpace space = factory.newSpace(100,-1, g, false);
        StateChunk chunk = (StateChunk) space.createAndMark(ChunkType.STATE_CHUNK, 0, 0, 0);
        EGraph egraph = (EGraph) chunk.getOrCreateAt(0, Type.EGRAPH);
        ENode eNode = egraph.newNode();
        for (int i = 0; i < 1000000; i++) {
            eNode.setAt(i, Type.INT, i);
        }
        for (int i = 0; i < 1000000; i++) {
            Assert.assertEquals(i, (int) eNode.getAt(i));
        }
        egraph.free();
        space.free(chunk);
        space.freeAll();
    }

    @Test
    public void loadSaveTest() {

        Storage mock = new MockStorage();

        Graph g = GraphBuilder.newBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(mock)
                .build();

        g.connect(null);

        StateChunk chunk = (StateChunk) g.space().createAndMark(ChunkType.STATE_CHUNK, 0, 0, 0);
        chunk.set("name", Type.STRING, "ParentChunk");

        //test embedded graph attribute
        EGraph egraph = (EGraph) chunk.getOrCreateAt(0, Type.EGRAPH);
        //test primitive attribute
        ENode eNode = egraph.newNode();
        egraph.setRoot(eNode);
        eNode.set("self", Type.ENODE, eNode);
        eNode.set("name", Type.STRING, "root");
        ERelation eRelation = (ERelation) eNode.getOrCreate("children", Type.ERELATION);
        for (int i = 0; i < 9999; i++) {
            ENode loopNode = egraph.newNode();
            loopNode.set("name", Type.STRING, "node_" + i);
            eRelation.add(loopNode);
        }
        //long before = System.currentTimeMillis();
        g.save(null);
        long after = System.currentTimeMillis();
        //System.out.println("save time:" + (after - before));
        g.disconnect(null);

        g = GraphBuilder.newBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(mock)
                .build();
        g.connect(null);
        //final long before2 = System.currentTimeMillis();
        g.space().getOrLoadAndMark(ChunkType.STATE_CHUNK, 0, 0, 0, res -> {
            StateChunk loaded = (StateChunk) res;
            EGraph egraphLoaded = (EGraph) loaded.getAt(0);
            //long after2 = System.currentTimeMillis();
            //System.out.println("loading time:" + (after2 - before2));
            Assert.assertEquals(egraph.toString(), egraphLoaded.toString());
            ENode rootLoaded = egraphLoaded.root();
            Assert.assertTrue(rootLoaded != null);
            Assert.assertEquals(rootLoaded.toString(), rootLoaded.get("self").toString());
        });

    }

    @Test
    public void mixedLoadSaveTest() {
        Storage mock = new MockStorage();

        Graph g = GraphBuilder.newBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(mock)
                .build();

        g.connect(null);
        StateChunk chunk = (StateChunk) g.space().createAndMark(ChunkType.STATE_CHUNK, 0, 0, 0);
        chunk.setAt(0, Type.STRING, "AdditionalString");
        EGraph egraph = (EGraph) chunk.getOrCreateAt(1, Type.EGRAPH);
        chunk.setAt(2, Type.STRING, "AdditionalStringAfter");
        ENode eNode = egraph.newNode();
        eNode.set("name", Type.STRING, "myEnode");


        g.save(null);

        g.disconnect(null);

        g = GraphBuilder.newBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(mock)
                .build();
        g.connect(null);
        //final long before2 = System.currentTimeMillis();
        g.space().getOrLoadAndMark(ChunkType.STATE_CHUNK, 0, 0, 0, res -> {
            StateChunk loaded = (StateChunk) res;
            Assert.assertEquals(loaded.getAt(0), chunk.getAt(0));
            Assert.assertEquals(loaded.getAt(2), chunk.getAt(2));

            //System.out.println(loaded);

            /*
            EGraph egraphLoaded = (EGraph) loaded.get(0);
            //long after2 = System.currentTimeMillis();
            //System.out.println("loading time:" + (after2 - before2));
            Assert.assertEquals(egraph.toString(), egraphLoaded.toString());
            ENode rootLoaded = egraphLoaded.root();
            Assert.assertTrue(rootLoaded != null);
            Assert.assertEquals(rootLoaded.toString(), rootLoaded.get("self").toString());
            */
        });


    }

    @Test
    public void volatileTest() {
        Graph g = GraphBuilder.newBuilder().withScheduler(new NoopScheduler()).build();
        g.connect(null);

        ChunkSpace space = factory.newSpace(100, -1, g, false);

        EGraph eGraph = space.newVolatileGraph();
        ENode eNode = eGraph.newNode();

        Assert.assertNotNull(eGraph);
        Assert.assertNotNull(eNode);

        LMatrix lmat = (LMatrix) eNode.getOrCreate("lmat", Type.LMATRIX);
        lmat.appendColumn(new long[]{1, 2, 3});
        lmat.set(1, 0, 5L);

        DMatrix mat = (DMatrix) eNode.getOrCreate("dmat", Type.DMATRIX);
        mat.appendColumn(new double[]{1.0, 2.0, 3.0});
        mat.set(1, 0, 0.7);

        ERelation eRel = (ERelation) eNode.getOrCreate("erel", Type.ERELATION);
        eRel.add(eNode);

        eGraph.free();
        space.freeAll();

    }


}
