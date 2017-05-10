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
import greycat.chunk.ChunkType;
import greycat.chunk.LTimeTreeChunk;
import greycat.chunk.TreeWalker;
import greycat.internal.CoreConstants;
import greycat.struct.Buffer;
import greycat.utility.Base64;
import greycat.utility.HashHelper;

public class HeapLTimeTreeChunk implements LTimeTreeChunk {

    //constants definition
    private static final int META_SIZE = 3;

    private final long _index;
    private final HeapChunkSpace _space;
    private int _root = -1;
    private int[] _back_meta;
    private long[] _kv;
    private boolean[] _colors;
    private volatile long _magic;
    private volatile int _size = 0;
    private long _hash;
    private boolean _inSync;
    //extra temporal element
    private volatile long _extra;
    private volatile long _extra2;

    public HeapLTimeTreeChunk(final HeapChunkSpace p_space, final long p_index) {
        _space = p_space;
        _index = p_index;
        _magic = 0;
        _hash = 0;
        _extra = 0;
        _extra2 = 0;
        _inSync = true;
    }

    @Override
    public final long extra() {
        return _extra;
    }

    @Override
    public final void setExtra(long extraValue) {
        _extra = extraValue;
    }

    @Override
    public final long extra2() {
        return _extra2;
    }

    @Override
    public final void setExtra2(long extraValue) {
        _extra2 = extraValue;
    }

    @Override
    public final long world() {
        return _space.worldByIndex(_index);
    }

    @Override
    public final long time() {
        return _space.timeByIndex(_index);
    }

    @Override
    public final long id() {
        return _space.idByIndex(_index);
    }

    @Override
    public final int size() {
        return _size;
    }

    @Override
    public synchronized final boolean inSync() {
        return _inSync;
    }

    @Override
    public synchronized final boolean sync(long remoteHash) {
        if (_inSync && remoteHash != _hash) {
            _inSync = false;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized final void range(final long startKey, final long endKey, final long maxElements, final TreeWalker walker) {
        //lock and load fromVar main memory
        int nbElements = 0;
        int indexEnd = internal_previousOrEqual_index(endKey);
        while (indexEnd != -1 && key(indexEnd) >= startKey && nbElements < maxElements) {
            walker.elem(key(indexEnd));
            nbElements++;
            indexEnd = internal_previous(indexEnd);
        }
    }

    @Override
    public synchronized final void save(Buffer buffer) {
        final long beginIndex = buffer.writeIndex();
        if (_extra != CoreConstants.NULL_LONG && _extra != 0) {
            Base64.encodeLongToBuffer(_extra, buffer);
            buffer.write(CoreConstants.CHUNK_SEP);
        }
        if (_extra2 != CoreConstants.NULL_LONG && _extra2 != 0) {
            Base64.encodeLongToBuffer(_extra2, buffer);
            buffer.write(CoreConstants.CHUNK_SEP);
        }
        Base64.encodeIntToBuffer(_size, buffer);
        for (int i = 0; i < _size; i++) {
            buffer.write(CoreConstants.CHUNK_VAL_SEP);
            Base64.encodeLongToBuffer(this._kv[i * 2], buffer);
            buffer.write(CoreConstants.CHUNK_VAL_SEP);
            Base64.encodeLongToBuffer(this._kv[(i * 2) + 1], buffer);
        }
        _hash = HashHelper.hashBuffer(buffer, beginIndex, buffer.writeIndex());
        /*if (_diff != null) {
            CoreConstants.fillBooleanArray(_diff, false);
        }*/
    }

    @Override
    public synchronized final void saveDiff(Buffer buffer) {
        if (_hash == Constants.EMPTY_HASH) {
            final long beginIndex = buffer.writeIndex();
            if (_extra != CoreConstants.NULL_LONG && _extra != 0) {
                Base64.encodeLongToBuffer(_extra, buffer);
                buffer.write(CoreConstants.CHUNK_SEP);
            }
            if (_extra2 != CoreConstants.NULL_LONG && _extra2 != 0) {
                Base64.encodeLongToBuffer(_extra2, buffer);
                buffer.write(CoreConstants.CHUNK_SEP);
            }
            Base64.encodeIntToBuffer(_size, buffer);
            for (int i = 0; i < _size; i++) {
                // if (_diff[i]) {
                buffer.write(CoreConstants.CHUNK_VAL_SEP);
                Base64.encodeLongToBuffer(this._kv[i * 2], buffer);
                buffer.write(CoreConstants.CHUNK_VAL_SEP);
                Base64.encodeLongToBuffer(this._kv[(i * 2) + 1], buffer);
                // }
            }
            _hash = HashHelper.hashBuffer(buffer, beginIndex, buffer.writeIndex());
            //CoreConstants.fillBooleanArray(_diff, false);
        }
    }

    @Override
    public synchronized final void load(final Buffer buffer) {
        internal_load(buffer, true);
        //TODO reset _dirty
    }

    @Override
    public synchronized void loadDiff(final Buffer buffer) {
        if (internal_load(buffer, false) && _hash != Constants.EMPTY_HASH) {
            _hash = Constants.EMPTY_HASH;
            if (_space != null) {
                _space.notifyUpdate(_index);
            }
        }
    }

    @Override
    public final long hash() {
        return _hash;
    }

    private boolean internal_load(final Buffer buffer, final boolean initial) {
        if (buffer == null || buffer.length() == 0) {
            return false;
        }
        boolean isDirty = false;
        long cursor = 0;
        long previous = 0;
        long payloadSize = buffer.length();
        boolean isFirst = true;
        boolean isFirstExtra = true;
        long key = -1;
        boolean waitingValue = false;
        while (cursor < payloadSize) {
            final byte current = buffer.read(cursor);
            switch (current) {
                case Constants.CHUNK_SEP:
                    if (isFirstExtra) {
                        _extra = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        previous = cursor + 1;
                        isFirstExtra = false;
                    } else {
                        _extra2 = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        previous = cursor + 1;
                    }
                    break;
                case Constants.CHUNK_VAL_SEP:
                    if (isFirst) {
                        final int treeSize = Base64.decodeToIntWithBounds(buffer, previous, cursor);
                        final int closePowerOfTwo = (int) Math.pow(2, Math.ceil(Math.log(treeSize) / Math.log(2)));
                        reallocate(closePowerOfTwo);
                        previous = cursor + 1;
                        isFirst = false;
                    } else {
                        if (!waitingValue) {
                            key = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                            waitingValue = true;
                        } else {
                            boolean insertResult = internal_insert(key, Base64.decodeToLongWithBounds(buffer, previous, cursor), initial);
                            isDirty = isDirty || insertResult;
                            waitingValue = false;
                        }
                        previous = cursor + 1;
                    }
                    break;
            }
            cursor++;
        }
        if (waitingValue) {
            boolean insertResult = internal_insert(key, Base64.decodeToLongWithBounds(buffer, previous, cursor), initial);
            isDirty = isDirty || insertResult;
        }
        return isDirty;
    }

    @Override
    public final long index() {
        return _index;
    }

    @Override
    public synchronized long previous(long key) {
        //lock and load fromVar main memory
        long resultKey;
        int result = internal_previous_index(key);

        if (result != -1) {
            resultKey = key(result);
        } else {
            resultKey = CoreConstants.NULL_LONG;
        }
        return resultKey;
    }

    @Override
    public synchronized long next(long key) {
        long resultKey;
        int result = internal_previousOrEqual_index(key);
        if (result != -1) {
            result = internal_next(result);
        }
        if (result != -1) {
            resultKey = key(result);
        } else {
            resultKey = CoreConstants.NULL_LONG;
        }
        return resultKey;
    }

    @Override
    public synchronized final long previousOrEqual(long key) {
        long resultKey;
        int result = internal_previousOrEqual_index(key);

        if (result != -1) {
            resultKey = key(result);
        } else {
            resultKey = CoreConstants.NULL_LONG;
        }
        return resultKey;
    }

    @Override
    public final long magic() {
        return this._magic;
    }

    @Override
    public synchronized final void insert(final long p_key, final long p_value) {
        if (internal_insert(p_key, p_value, false)) {
            internal_set_dirty();
        }
    }

    @Override
    public synchronized final void unsafe_insert(final long p_key, final long p_value) {
        internal_insert(p_key, p_value, false);
    }

    @Override
    public final byte chunkType() {
        return ChunkType.TIME_TREE_CHUNK;
    }

    private void reallocate(int newCapacity) {
        if (_kv != null && newCapacity <= (_kv.length / 2)) {
            return;
        }
        long[] new_back_kv = new long[newCapacity * 2];
        if (_kv != null) {
            System.arraycopy(_kv, 0, new_back_kv, 0, _size * 2);
        }
        /*
        boolean[] new_back_diff = new boolean[newCapacity];
        CoreConstants.fillBooleanArray(new_back_diff, false);
        if (_diff != null) {
            System.arraycopy(_diff, 0, new_back_diff, 0, _size);
        }*/
        boolean[] new_back_colors = new boolean[newCapacity];
        if (_colors != null) {
            System.arraycopy(_colors, 0, new_back_colors, 0, _size);
            for (int i = _size; i < newCapacity; i++) {
                new_back_colors[i] = false;
            }
        }
        int[] new_back_meta = new int[newCapacity * META_SIZE];
        if (_back_meta != null) {
            System.arraycopy(_back_meta, 0, new_back_meta, 0, _size * META_SIZE);
            for (int i = _size * META_SIZE; i < newCapacity * META_SIZE; i++) {
                new_back_meta[i] = -1;
            }
        }
        _back_meta = new_back_meta;
        _kv = new_back_kv;
        _colors = new_back_colors;
        //_diff = new_back_diff;
    }

    private long key(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        return _kv[p_currentIndex * 2];
    }

    private long value(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        return _kv[(p_currentIndex * 2) + 1];
    }


    private void setKeyValue(int p_currentIndex, long p_key, long p_value, boolean initial) {
        _kv[p_currentIndex * 2] = p_key;
        _kv[(p_currentIndex * 2) + 1] = p_value;
        /*if (!initial) {
            _diff[p_currentIndex] = true;
        }*/
    }



    /*
    protected final long value(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        return _k[(p_currentIndex) + 1];
    }

    private void setValue(int p_currentIndex, long p_paramIndex) {
        _k[(p_currentIndex) + 1] = p_paramIndex;
    }*/

    private int left(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        return _back_meta[p_currentIndex * META_SIZE];
    }

    private void setLeft(int p_currentIndex, int p_paramIndex) {
        _back_meta[p_currentIndex * META_SIZE] = p_paramIndex;
    }

    private int right(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        return _back_meta[(p_currentIndex * META_SIZE) + 1];
    }

    private void setRight(int p_currentIndex, int p_paramIndex) {
        _back_meta[(p_currentIndex * META_SIZE) + 1] = p_paramIndex;
    }

    private int parent(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        return _back_meta[(p_currentIndex * META_SIZE) + 2];
    }

    private void setParent(int p_currentIndex, int p_paramIndex) {
        _back_meta[(p_currentIndex * META_SIZE) + 2] = p_paramIndex;
    }

    private boolean color(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return true;
        }
        return _colors[p_currentIndex];
    }

    private void setColor(int p_currentIndex, boolean p_paramIndex) {
        _colors[p_currentIndex] = p_paramIndex;
    }

    private int grandParent(int p_currentIndex) {
        if (p_currentIndex == -1) {
            return -1;
        }
        if (parent(p_currentIndex) != -1) {
            return parent(parent(p_currentIndex));
        } else {
            return -1;
        }
    }

    private int sibling(int p_currentIndex) {
        if (parent(p_currentIndex) == -1) {
            return -1;
        } else {
            if (p_currentIndex == left(parent(p_currentIndex))) {
                return right(parent(p_currentIndex));
            } else {
                return left(parent(p_currentIndex));
            }
        }
    }

    private int uncle(int p_currentIndex) {
        if (parent(p_currentIndex) != -1) {
            return sibling(parent(p_currentIndex));
        } else {
            return -1;
        }
    }

    private int internal_previous(int p_index) {
        int p = p_index;
        if (left(p) != -1) {
            p = left(p);
            while (right(p) != -1) {
                p = right(p);
            }
            return p;
        } else {
            if (parent(p) != -1) {
                if (p == right(parent(p))) {
                    return parent(p);
                } else {
                    while (parent(p) != -1 && p == left(parent(p))) {
                        p = parent(p);
                    }
                    return parent(p);
                }
            } else {
                return -1;
            }
        }
    }

    private int internal_next(int p_index) {
        int p = p_index;
        if (right(p) != -1) {
            p = right(p);
            while (left(p) != -1) {
                p = left(p);
            }
            return p;
        } else {
            if (parent(p) != -1) {
                if (p == left(parent(p))) {
                    return parent(p);
                } else {
                    while (parent(p) != -1 && p == right(parent(p))) {
                        p = parent(p);
                    }
                    return parent(p);
                }

            } else {
                return -1;
            }
        }
    }

    /*
    private long lookup(long p_key) {
        int n = _root;
        if (n == -1) {
            return CoreConstants.NULL_LONG;
        }
        while (n != -1) {
            if (p_key == key(n)) {
                return key(n);
            } else {
                if (p_key < key(n)) {
                    n = left(n);
                } else {
                    n = right(n);
                }
            }
        }
        return n;
    }
    */


    private int internal_previousOrEqual_index(long p_key) {
        int p = _root;
        if (p == -1) {
            return p;
        }
        while (p != -1) {
            if (p_key == key(p)) {
                return p;
            }
            if (p_key > key(p)) {
                if (right(p) != -1) {
                    p = right(p);
                } else {
                    return p;
                }
            } else {
                if (left(p) != -1) {
                    p = left(p);
                } else {
                    int parent = parent(p);
                    long ch = p;
                    while (parent != -1 && ch == left(parent)) {
                        ch = parent;
                        parent = parent(parent);
                    }
                    return parent;
                }
            }
        }
        return -1;
    }

    private int internal_previous_index(long p_key) {
        int p = _root;
        if (p == -1) {
            return p;
        }
        while (p != -1) {
            if (p_key > key(p)) {
                if (right(p) != -1) {
                    p = right(p);
                } else {
                    return p;
                }
            } else {
                if (left(p) != -1) {
                    p = left(p);
                } else {
                    int parent = parent(p);
                    long ch = p;
                    while (parent != -1 && ch == left(parent)) {
                        ch = parent;
                        parent = parent(parent);
                    }
                    return parent;
                }
            }
        }
        return -1;
    }

    private void rotateLeft(int n) {
        int r = right(n);
        replaceNode(n, r);
        setRight(n, left(r));
        if (left(r) != -1) {
            setParent(left(r), n);
        }
        setLeft(r, n);
        setParent(n, r);
    }

    private void rotateRight(int n) {
        int l = left(n);
        replaceNode(n, l);
        setLeft(n, right(l));
        if (right(l) != -1) {
            setParent(right(l), n);
        }
        setRight(l, n);
        setParent(n, l);
    }

    private void replaceNode(int oldn, int newn) {
        if (parent(oldn) == -1) {
            _root = newn;
        } else {
            if (oldn == left(parent(oldn))) {
                setLeft(parent(oldn), newn);
            } else {
                setRight(parent(oldn), newn);
            }
        }
        if (newn != -1) {
            setParent(newn, parent(oldn));
        }
    }

    private void insertCase1(int n) {
        if (parent(n) == -1) {
            setColor(n, true);
        } else {
            insertCase2(n);
        }
    }

    private void insertCase2(int n) {
        if (!color(parent(n))) {
            insertCase3(n);
        }
    }

    private void insertCase3(int n) {
        if (!color(uncle(n))) {
            setColor(parent(n), true);
            setColor(uncle(n), true);
            setColor(grandParent(n), false);
            insertCase1(grandParent(n));
        } else {
            insertCase4(n);
        }
    }

    private void insertCase4(int n_n) {
        int n = n_n;
        if (n == right(parent(n)) && parent(n) == left(grandParent(n))) {
            rotateLeft(parent(n));
            n = left(n);
        } else {
            if (n == left(parent(n)) && parent(n) == right(grandParent(n))) {
                rotateRight(parent(n));
                n = right(n);
            }
        }
        insertCase5(n);
    }

    private void insertCase5(int n) {
        setColor(parent(n), true);
        setColor(grandParent(n), false);
        if (n == left(parent(n)) && parent(n) == left(grandParent(n))) {
            rotateRight(grandParent(n));
        } else {
            rotateLeft(grandParent(n));
        }
    }

    private boolean internal_insert(long p_key, long p_value, boolean initial) {
        if (_kv == null || (_kv.length / 2) == _size) {
            int length = _size;
            if (length == 0) {
                length = Constants.MAP_INITIAL_CAPACITY;
            } else {
                length = length * 2;
            }
            reallocate(length);
        }
        int newIndex = _size;
        if (newIndex == 0) {
            setKeyValue(newIndex, p_key, p_value, initial);
            setColor(newIndex, false);
            setLeft(newIndex, -1);
            setRight(newIndex, -1);
            setParent(newIndex, -1);
            _root = newIndex;
            _size = 1;
        } else {
            int n = _root;
            while (true) {
                if (p_key == key(n)) {
                    return false;
                } else if (p_key < key(n)) {
                    if (left(n) == -1) {
                        setKeyValue(newIndex, p_key, p_value, initial);
                        setColor(newIndex, false);
                        setLeft(newIndex, -1);
                        setRight(newIndex, -1);
                        setParent(newIndex, -1);
                        setLeft(n, newIndex);
                        _size++;
                        break;
                    } else {
                        n = left(n);
                    }
                } else {
                    if (right(n) == -1) {
                        setKeyValue(newIndex, p_key, p_value, initial);
                        setColor(newIndex, false);
                        setLeft(newIndex, -1);
                        setRight(newIndex, -1);
                        setParent(newIndex, -1);
                        setRight(n, newIndex);
                        _size++;
                        break;
                    } else {
                        n = right(n);
                    }
                }
            }
            setParent(newIndex, n);
        }
        insertCase1(newIndex);
        return true;
    }

    private void internal_set_dirty() {
        _magic = _magic + 1;
        if (_space != null && _hash != Constants.EMPTY_HASH) {
            _hash = Constants.EMPTY_HASH;
            _space.notifyUpdate(_index);
        }
    }

     /*
    public void delete(long key) {
        TreeNode n = lookup(key);
        if (n == null) {
            return;
        } else {
            _size--;
            if (n.getLeft() != null && n.getRight() != null) {
                // Copy domainKey/value fromVar predecessor and done delete it instead
                TreeNode pred = n.getLeft();
                while (pred.getRight() != null) {
                    pred = pred.getRight();
                }
                n.key = pred.key;
                n = pred;
            }
            TreeNode child;
            if (n.getRight() == null) {
                child = n.getLeft();
            } else {
                child = n.getRight();
            }
            if (nodeColor(n) == true) {
                n.color = nodeColor(child);
                deleteCase1(n);
            }
            replaceNode(n, child);
        }
    }

    private void deleteCase1(TreeNode n) {
        if (n.getParent() == null) {
            return;
        } else {
            deleteCase2(n);
        }
    }

    private void deleteCase2(TreeNode n) {
        if (nodeColor(n.sibling()) == false) {
            n.getParent().color = false;
            n.sibling().color = true;
            if (n == n.getParent().getLeft()) {
                rotateLeft(n.getParent());
            } else {
                rotateRight(n.getParent());
            }
        }
        deleteCase3(n);
    }

    private void deleteCase3(TreeNode n) {
        if (nodeColor(n.getParent()) == true && nodeColor(n.sibling()) == true && nodeColor(n.sibling().getLeft()) == true && nodeColor(n.sibling().getRight()) == true) {
            n.sibling().color = false;
            deleteCase1(n.getParent());
        } else {
            deleteCase4(n);
        }
    }

    private void deleteCase4(TreeNode n) {
        if (nodeColor(n.getParent()) == false && nodeColor(n.sibling()) == true && nodeColor(n.sibling().getLeft()) == true && nodeColor(n.sibling().getRight()) == true) {
            n.sibling().color = false;
            n.getParent().color = true;
        } else {
            deleteCase5(n);
        }
    }

    private void deleteCase5(TreeNode n) {
        if (n == n.getParent().getLeft() && nodeColor(n.sibling()) == true && nodeColor(n.sibling().getLeft()) == false && nodeColor(n.sibling().getRight()) == true) {
            n.sibling().color = false;
            n.sibling().getLeft().color = true;
            rotateRight(n.sibling());
        } else if (n == n.getParent().getRight() && nodeColor(n.sibling()) == true && nodeColor(n.sibling().getRight()) == false && nodeColor(n.sibling().getLeft()) == true) {
            n.sibling().color = false;
            n.sibling().getRight().color = true;
            rotateLeft(n.sibling());
        }
        deleteCase6(n);
    }

    private void deleteCase6(TreeNode n) {
        n.sibling().color = nodeColor(n.getParent());
        n.getParent().color = true;
        if (n == n.getParent().getLeft()) {
            n.sibling().getRight().color = true;
            rotateLeft(n.getParent());
        } else {
            n.sibling().getLeft().color = true;
            rotateRight(n.getParent());
        }
    }*/

}
