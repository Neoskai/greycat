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
import greycat.struct.Buffer;
import greycat.struct.LMatrix;
import greycat.utility.Base64;

import java.util.Arrays;
import java.util.Random;

class HeapLMatrix implements LMatrix {

    private static final int INDEX_ROWS = 0;
    private static final int INDEX_COLUMNS = 1;
    private static final int INDEX_MAX_COLUMN = 2;
    private static final int INDEX_OFFSET = 3;

    private final HeapContainer parent;
    private long[] backend = null;
    private boolean aligned = true;

    HeapLMatrix(final HeapContainer p_parent, final HeapLMatrix origin) {
        parent = p_parent;
        if (origin != null) {
            aligned = false;
            backend = origin.backend;
        }
    }

    @Override
    public final LMatrix init(final int rows, final int columns) {
        synchronized (parent) {
            internal_init(rows, columns);
        }
        parent.declareDirty();
        return this;
    }

    private void internal_init(final int rows, final int columns) {
        //clean backend for OffHeap version
        backend = new long[rows * columns + INDEX_OFFSET];
        backend[INDEX_ROWS] = rows;
        backend[INDEX_COLUMNS] = columns;
        backend[INDEX_MAX_COLUMN] = columns;//direct allocation
        aligned = true;
    }

    @Override
    public final LMatrix appendColumn(long[] newColumn) {
        synchronized (parent) {
            internal_appendColumn(newColumn);
            parent.declareDirty();
        }
        return this;
    }

    private void internal_appendColumn(long[] newColumn) {
        int nbRows;
        int nbColumns;
        int nbMaxColumn;
        if (backend == null) {
            nbRows = newColumn.length;
            nbColumns = Constants.MAP_INITIAL_CAPACITY;
            nbMaxColumn = 0;
            backend = new long[nbRows * nbColumns + INDEX_OFFSET];
            backend[INDEX_ROWS] = nbRows;
            backend[INDEX_COLUMNS] = nbColumns;
            backend[INDEX_MAX_COLUMN] = nbMaxColumn;
        } else {
            nbColumns = (int) backend[INDEX_COLUMNS];
            nbRows = (int) backend[INDEX_ROWS];
            nbMaxColumn = (int) backend[INDEX_MAX_COLUMN];
        }
        if (!aligned || nbMaxColumn == nbColumns) {
            if (nbMaxColumn == nbColumns) {
                nbColumns = nbColumns * 2;
                backend[INDEX_COLUMNS] = nbColumns;
                final int newLength = nbColumns * nbRows + INDEX_OFFSET;
                long[] next_backend = new long[newLength];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            } else {
                //direct copy
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
        }
        //just insert
        System.arraycopy(newColumn, 0, backend, (nbMaxColumn * nbRows) + INDEX_OFFSET, newColumn.length);
        backend[INDEX_MAX_COLUMN] = nbMaxColumn + 1;
    }

    @Override
    public final LMatrix fill(long value) {
        synchronized (parent) {
            internal_fill(value);
        }
        return this;
    }

    private void internal_fill(long value) {
        if (backend != null) {
            if (!aligned) {
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
            Arrays.fill(backend, INDEX_OFFSET, backend.length - INDEX_OFFSET, value);
            backend[INDEX_MAX_COLUMN] = backend[INDEX_COLUMNS];
            parent.declareDirty();
        }
    }

    @Override
    public LMatrix fillWith(long[] values) {
        synchronized (parent) {
            internal_fillWith(values);
        }
        return this;
    }

    private void internal_fillWith(long[] values) {
        if (backend != null) {
            if (!aligned) {
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
            //reInit ?
            System.arraycopy(values, 0, backend, INDEX_OFFSET, values.length);
            parent.declareDirty();
        }
    }

    @Override
    public LMatrix fillWithRandom(long min, long max, long seed) {
        synchronized (parent) {
            internal_fillWithRandom(min, max, seed);
        }
        return this;
    }

    private void internal_fillWithRandom(long min, long max, long seed) {
        Random rand = new Random();
        rand.setSeed(seed);
        if (backend != null) {
            if (!aligned) {
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
            for (int i = 0; i < backend[INDEX_ROWS] * backend[INDEX_COLUMNS]; i++) {
                backend[i + INDEX_OFFSET] = rand.nextInt() * (max - min) + min;
            }
            parent.declareDirty();
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public final int rows() {
        int result = 0;
        synchronized (parent) {
            if (backend != null) {
                result = (int) backend[INDEX_ROWS];
            }
        }
        return result;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public final int columns() {
        int result = 0;
        synchronized (parent) {
            if (backend != null) {
                result = (int) backend[INDEX_MAX_COLUMN];
            }
        }
        return result;
    }

    @Override
    public final long[] column(int index) {
        long[] result;
        synchronized (parent) {
            final int nbRows = (int) backend[INDEX_ROWS];
            result = new long[nbRows];
            System.arraycopy(backend, INDEX_OFFSET + (index * nbRows), result, 0, nbRows);
        }
        return result;
    }

    @Override
    public final long get(int rowIndex, int columnIndex) {
        long result = 0;
        synchronized (parent) {
            if (backend != null) {
                final int nbRows = (int) backend[INDEX_ROWS];
                result = backend[INDEX_OFFSET + rowIndex + columnIndex * nbRows];
            }
        }
        return result;
    }

    @Override
    public final LMatrix set(int rowIndex, int columnIndex, long value) {
        synchronized (parent) {
            internal_set(rowIndex, columnIndex, value);
        }
        return this;
    }

    private void internal_set(int rowIndex, int columnIndex, long value) {
        if (backend != null) {
            if (!aligned) {
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
            final int nbRows = (int) backend[INDEX_ROWS];
            backend[INDEX_OFFSET + rowIndex + columnIndex * nbRows] = value;
            parent.declareDirty();
        }
    }

    @Override
    public LMatrix add(int rowIndex, int columnIndex, long value) {
        synchronized (parent) {
            internal_add(rowIndex, columnIndex, value);
        }
        return this;
    }

    private void internal_add(int rowIndex, int columnIndex, long value) {
        if (backend != null) {
            if (!aligned) {
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
            final int nbRows = (int) backend[INDEX_ROWS];
            backend[INDEX_OFFSET + rowIndex + columnIndex * nbRows] = value + backend[INDEX_OFFSET + rowIndex + columnIndex * nbRows];
            parent.declareDirty();
        }
    }

    @Override
    public final long[] data() {
        long[] copy = null;
        synchronized (parent) {
            if (backend != null) {
                copy = new long[backend.length - INDEX_OFFSET];
                System.arraycopy(backend, INDEX_OFFSET, copy, 0, backend.length - INDEX_OFFSET);
            }
        }
        return copy;
    }

    @Override
    public int leadingDimension() {
        if (backend == null) {
            return 0;
        }
        return (int) Math.max(backend[INDEX_COLUMNS], backend[INDEX_ROWS]);
    }

    @Override
    public long unsafeGet(int index) {
        long result = 0;
        synchronized (parent) {
            if (backend != null) {
                result = backend[INDEX_OFFSET + index];
            }
        }
        return result;
    }

    @Override
    public LMatrix unsafeSet(int index, long value) {
        synchronized (parent) {
            internal_unsafeSet(index, value);
        }
        return this;
    }

    private void internal_unsafeSet(int index, long value) {
        if (backend != null) {
            if (!aligned) {
                long[] next_backend = new long[backend.length];
                System.arraycopy(backend, 0, next_backend, 0, backend.length);
                backend = next_backend;
                aligned = true;
            }
            backend[INDEX_OFFSET + index] = value;
            parent.declareDirty();
        }
    }

    long[] unsafe_data() {
        return backend;
    }

    void unsafe_init(int size) {
        backend = new long[size];
        backend[INDEX_ROWS] = 0;
        backend[INDEX_COLUMNS] = 0;
        aligned = true;
    }

    void unsafe_set(long index, long value) {
        backend[(int) index] = value;
    }

    public final long load(final Buffer buffer, final long offset, final long max) {
        long cursor = offset;
        byte current = buffer.read(cursor);
        boolean isFirst = true;
        long previous = offset;
        long elemIndex = 0;
        while (cursor < max && current != Constants.CHUNK_SEP && current != Constants.CHUNK_ENODE_SEP && current != Constants.CHUNK_ESEP) {
            if (current == Constants.CHUNK_VAL_SEP) {
                if (isFirst) {
                    unsafe_init(Base64.decodeToIntWithBounds(buffer, previous, cursor));
                    isFirst = false;
                } else {
                    unsafe_set(elemIndex, Base64.decodeToLongWithBounds(buffer, previous, cursor));
                    elemIndex++;
                }
                previous = cursor + 1;
            }
            cursor++;
            if (cursor < max) {
                current = buffer.read(cursor);
            }
        }
        if(previous == cursor) {
            unsafe_init(0);
        } else if (isFirst) {
            unsafe_init(Base64.decodeToIntWithBounds(buffer, previous, cursor));
        } else {
            unsafe_set(elemIndex, Base64.decodeToLongWithBounds(buffer, previous, cursor));
        }
        return cursor;
    }

}
