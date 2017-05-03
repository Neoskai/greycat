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
package greycatTest.internal.task;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Node;
import greycat.Type;
import greycat.base.BaseTaskResult;
import greycat.internal.heap.HeapBuffer;
import greycat.struct.Buffer;
import org.junit.Assert;
import org.junit.Test;

public class BaseTaskResultTest {


    @Test
    public void loadSaveTest() {

        Graph graph = GraphBuilder.newBuilder().build();
        graph.connect((connected) -> {

            BaseTaskResult result = new BaseTaskResult(null, false);
            result.add(graph.newNode(0, 0).set("name", Type.STRING, "node").set("value", Type.DOUBLE, 4.8));

            Buffer buffer = new HeapBuffer();
            result.saveToBuffer(buffer);

            BaseTaskResult resultBack = new BaseTaskResult(null, false);
            resultBack.load(buffer,0, graph);
            resultBack.loadRefs(graph, done->{
                Assert.assertEquals(result.size(), resultBack.size());
                Assert.assertEquals(((Node)result.get(0)).id(), ((Node)resultBack.get(0)).id());
            });

        });


    }

}
