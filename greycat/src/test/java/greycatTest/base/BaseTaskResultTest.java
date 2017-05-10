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
package greycatTest.base;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.base.BaseTaskResult;
import greycat.internal.heap.HeapBuffer;
import greycat.struct.Buffer;
import org.junit.Assert;
import org.junit.Test;

public class BaseTaskResultTest {

    @Test
    public void saveLoadTest() {
        Graph graph = GraphBuilder.newBuilder().build();
        graph.connect(connected -> {
            try {
                BaseTaskResult<String> taskResultOrigin = new BaseTaskResult("ResultContent", false);
                taskResultOrigin.setException(new RuntimeException("Exception text"));

                Buffer buffer = new HeapBuffer();
                taskResultOrigin.saveToBuffer(buffer);

                BaseTaskResult taskResultLoaded = new BaseTaskResult(null, false);
                taskResultLoaded.load(buffer,0, graph, null);

            } catch (Throwable t) {
                Assert.fail("Exception raised !");
                t.printStackTrace();
            }
            graph.disconnect(null);
        });
    }


}
