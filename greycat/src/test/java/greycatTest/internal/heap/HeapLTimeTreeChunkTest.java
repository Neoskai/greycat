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
package greycatTest.internal.heap;

import greycat.internal.heap.HeapBuffer;
import greycat.internal.heap.HeapChunkSpace;
import greycat.internal.heap.HeapLTimeTreeChunk;
import greycat.struct.Buffer;
import org.junit.Assert;
import org.junit.Test;

public class HeapLTimeTreeChunkTest {

    @Test
    public void test() {
        HeapChunkSpace space = new HeapChunkSpace(100, 10, null, false);
        HeapLTimeTreeChunk tree = new HeapLTimeTreeChunk(space, -1);
        for (int i = 0; i < 100; i = i + 10) {
            tree.insert(i, i);
        }
        for (int i = 5; i < 100; i = i + 10) {
            Assert.assertEquals(tree.previous(i), i - 5);
        }
    }

    @Test
    public void loadSaveTest() {
        HeapChunkSpace space = new HeapChunkSpace(100, 10, null, false);
        HeapLTimeTreeChunk tree = new HeapLTimeTreeChunk(space, -1);
        for (int i = 0; i < 100; i = i + 10) {
            tree.insert(i, i);
        }
        Buffer buf = new HeapBuffer();
        tree.save(buf);
        HeapLTimeTreeChunk tree2 = new HeapLTimeTreeChunk(space, -1);
        tree2.load(buf);
        buf.free();
        for (int i = 5; i < 100; i = i + 10) {
            Assert.assertEquals(i - 5,tree2.previous(i));
        }
    }


}
