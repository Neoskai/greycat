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
import greycat.TaskContext;
import greycat.TaskResult;
import greycat.base.BaseTaskResult;
import greycat.internal.heap.HeapBuffer;
import greycat.internal.task.CoreTask;
import greycat.struct.Buffer;
import org.junit.Assert;
import org.junit.Test;

public class TaskResultSaveLoadTest {

    @Test
    public void test() {
        Graph graph = GraphBuilder.newBuilder().build();
        CoreTask task = new CoreTask();

        BaseTaskResult res = new BaseTaskResult(null, false);
        res.add("start");
        res.add(null);
        res.add("end");

        TaskContext ctx = task.prepare(graph, res, null);
        Buffer buf = new HeapBuffer();
        ctx.saveToBuffer(buf);

        TaskContext ctx2 = task.prepare(graph, null, null);
        ctx2.loadFromBuffer(buf, null);

        TaskResult res2 = ctx2.result();
        Assert.assertEquals(res2.get(0), "start");
        Assert.assertEquals(res2.get(1), null);
        Assert.assertEquals(res2.get(2), "end");

    }

}
