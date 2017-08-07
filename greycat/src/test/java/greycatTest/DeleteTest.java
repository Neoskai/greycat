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
package greycatTest;

import greycat.*;
import greycat.scheduler.NoopScheduler;
import greycat.struct.Relation;
import greycatTest.internal.MockStorage;
import org.junit.Assert;
import org.junit.Test;

import static greycat.Tasks.newTask;

public class DeleteTest {

    @Test
    public void test() {
        MockStorage storage = new MockStorage();
        Graph g = GraphBuilder.newBuilder().withScheduler(new NoopScheduler()).withStorage(storage).build();
        g.connect(null);
        g.save(null);
        Assert.assertEquals(0, storage.backend.size());
        Node n = g.newNode(0, 0);
        final long n_id = n.id();
        for (int i = 0; i < 10; i++) {
            g.lookup(0, i, n_id, result -> {
                result.forceSetAt(0, Type.LONG, result.time());
                result.free();
            });
        }
        g.save(null);
        Assert.assertEquals(14, storage.backend.size());
        n.drop(null);
        Assert.assertEquals(1, storage.backend.size());//only the generator remains
    }


    //@Test
    public void grandChildrenTest() {
        MockStorage storage = new MockStorage();
        final Graph graph_back = new GraphBuilder().withScheduler(new NoopScheduler()).withStorage(storage).withMemorySize(10000).build();
        graph_back.connect(null);

        graph_back.connect(null);
        graph_back.save(null);
        Assert.assertEquals(0, storage.backend.size());

        // Create a parent
        Node parent = graph_back.newNode(0, 0);
        graph_back.save(null);
        Assert.assertEquals(5, storage.backend.size());

        // Create a child and add it to 'children' relationship
        Node child = graph_back.newNode(0, 0);
        Relation children = (Relation) parent.getOrCreate("children", Type.RELATION);
        children.addNode(child);
        graph_back.save(null);
        Assert.assertEquals(10, storage.backend.size());

        // Create a grand-child and add it to 'children' relationship of the children
        Node gradchild = graph_back.newNode(0, 0);
        Relation grandChildren = (Relation) child.getOrCreate("children", Type.RELATION);
        grandChildren.addNode(gradchild);
        graph_back.save(null);

        long parentId = parent.id();



        newTask()
                .travelInTime("0")
                .lookup("" + parentId)
                /*
                ADD FOR MORE RED LINES
                .travelInTime("1")

                adds to the stack:
                java.lang.NullPointerException
                    at greycat.internal.heap.HeapChunkSpace.save(HeapChunkSpace.java:523)
                    at greycat.internal.CoreGraph.save(CoreGraph.java:348)
                    at greycat.internal.task.ActionSave.eval(ActionSave.java:30)
                    at greycat.internal.task.CoreTaskContext.continueTask(CoreTaskContext.java:479)
                    at greycat.internal.task.ActionRemove.eval(ActionRemove.java:47)
                */
                .setAsVar("parent")
                .traverse("children") // get children

                .setAsVar("children")
                .traverse("children") // get grand-children
                .delete()                   //Delete grand-children
                .readVar("children")
                .remove("children")         // remove children relation from child

                .delete()                   // delete children
                .readVar("parent")
                .remove("children")         // remove relation
                .save()
                .execute(graph_back, result -> {
                    //Assert.assertEquals(6, storage.backend.size());
                });
    }

}
