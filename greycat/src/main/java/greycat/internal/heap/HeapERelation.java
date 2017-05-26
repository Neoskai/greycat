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
package greycat.internal.heap;

import greycat.Constants;
import greycat.struct.ENode;
import greycat.struct.ERelation;

class HeapERelation implements ERelation {

    private ENode[] _back;
    private int _size;
    private int _capacity;
    private final HeapContainer parent;

    HeapERelation(final HeapContainer p_parent, final HeapERelation origin) {
        parent = p_parent;
        if (origin != null) {
            _back = new ENode[origin._capacity];
            System.arraycopy(origin._back, 0, _back, 0, origin._capacity);
            _size = origin._size;
            _capacity = origin._capacity;
        } else {
            _back = null;
            _size = 0;
            _capacity = 0;
        }
    }

    final void rebase(HeapEGraph newGraph) {
        for (int i = 0; i < _size; i++) {
            final HeapENode previous = (HeapENode) _back[i];
            _back[i] = newGraph._nodes[previous._id];
        }
    }

    @Override
    public final int size() {
        return _size;
    }

    @Override
    public final ENode[] nodes() {
        ENode[] copy = new ENode[_size];
        System.arraycopy(_back, 0, copy, 0, _size);
        return copy;
    }

    @Override
    public final ENode node(int index) {
        return _back[index];
    }

    @Override
    public final ERelation add(ENode eNode) {
        if (_capacity == _size) {
            if (_capacity == 0) {
                allocate(Constants.MAP_INITIAL_CAPACITY);
            } else {
                allocate(_capacity * 2);
            }
        }
        _back[_size] = eNode;
        _size++;
        if (parent != null) {
            parent.declareDirty();
        }
        return this;
    }

    @Override
    public final ERelation addAll(final ENode[] eNodes) {
        allocate(eNodes.length + _size);
        System.arraycopy(eNodes, 0, _back, _size, eNodes.length);
        if (parent != null) {
            parent.declareDirty();
        }
        return this;
    }

    @Override
    public final ERelation clear() {
        _size = 0;
        _back = null;
        if (parent != null) {
            parent.declareDirty();
        }
        return this;
    }

    @Override
    public final String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (int i = 0; i < _size; i++) {
            if (i != 0) {
                buffer.append(",");
            }
            buffer.append(((HeapENode) _back[i])._id);
        }
        buffer.append("]");
        return buffer.toString();
    }

    final void allocate(int newCapacity) {
        final int closePowerOfTwo = (int) Math.pow(2, Math.ceil(Math.log(newCapacity) / Math.log(2)));
        if (closePowerOfTwo > _capacity) {
            ENode[] new_back = new ENode[closePowerOfTwo];
            if (_back != null) {
                System.arraycopy(_back, 0, new_back, 0, _size);
            }
            _back = new_back;
            _capacity = closePowerOfTwo;
        }
    }

}
