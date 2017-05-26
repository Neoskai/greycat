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
package greycat.utility;

import greycat.Constants;

import java.util.Arrays;

public class L3GMap<T> {

    public static final int GROUP = 3;

    private int mapSize = 0;
    private int capacity = 0;
    public long[] keys = null;
    private Object[] values = null;
    private int[] next_hashs = null;
    private final boolean _withValue;

    public L3GMap(final boolean withValue) {
        this._withValue = withValue;
    }

    private void setKey(int i, long k1, long k2, long k3) {
        keys[i * GROUP] = k1;
        keys[i * GROUP + 1] = k2;
        keys[i * GROUP + 2] = k3;
    }

    private T value(int i) {
        return (T) values[i];
    }

    private void setValue(int i, T newValue) {
        values[i] = newValue;
    }

    private int next(int i) {
        return next_hashs[i];
    }

    private void setNext(int i, int newValue) {
        next_hashs[i] = newValue;
    }

    private int hash(int i, int capacity) {
        return next_hashs[i + capacity];
    }

    private void setHash(int i, int newValue, int capacity) {
        next_hashs[i + capacity] = newValue;
    }

    private void reallocate(int newCapacity) {
        if (newCapacity > capacity) {
            //extend keys
            long[] new_keys = new long[newCapacity * GROUP];
            if (keys != null) {
                System.arraycopy(keys, 0, new_keys, 0, capacity * GROUP);
            }
            keys = new_keys;
            //extend values
            if (_withValue) {
                Object[] new_values = new Object[newCapacity];
                if (values != null) {
                    System.arraycopy(values, 0, new_values, 0, capacity);
                }
                values = new_values;
            }
            int[] new_next_hashes = new int[newCapacity * 3];
            Arrays.fill(new_next_hashes, 0, (newCapacity * 3), -1);
            next_hashs = new_next_hashes;
            for (int i = 0; i < mapSize; i++) {
                int new_key_hash = (int) ((keys[i * GROUP] ^ keys[i * GROUP + 1] ^ keys[i * GROUP + 2]) % (newCapacity * 2));
                setNext(i, hash(new_key_hash, newCapacity));
                setHash(new_key_hash, i, newCapacity);
            }
            capacity = newCapacity;
        }
    }

    public final boolean contains(final long k1, final long k2, final long k3) {
        boolean result = false;
        if (keys != null) {
            final int hashIndex = (int) ((k1 ^ k2 ^ k3) % (capacity * 2));
            int m = hash(hashIndex, capacity);
            while (m >= 0) {
                if (k1 == keys[m * GROUP] && k2 == keys[m * GROUP + 1] && k3 == keys[m * GROUP + 2]) {
                    result = true;
                    break;
                }
                m = next(m);
            }
        }
        return result;
    }

    public final T get(final long k1, final long k2, final long k3) {
        T result = null;
        if (keys != null) {
            final int hashIndex = (int) ((k1 ^ k2 ^ k3) % (capacity * 2));
            int m = hash(hashIndex, capacity);
            while (m >= 0) {
                if (k1 == keys[m * GROUP] && k2 == keys[m * GROUP + 1] && k3 == keys[m * GROUP + 2]) {
                    result = value(m);
                    break;
                }
                m = next(m);
            }
        }
        return result;
    }

    /*
    public final void each(LongLongMapCallBack callback) {
        for (int i = 0; i < mapSize; i++) {
            callback.on(key(i), value(i));
        }
    }*/

    /*
    public final long getKey(int index) {
        return key(index);
    }*/

    public final int size() {
        return mapSize;
    }

    /*
    public final void remove(final long requestKey) {
        if (keys != null && mapSize != 0) {
            long hashCapacity = capacity * 2;
            int hashIndex = (int) HashHelper.longHash(requestKey, hashCapacity);
            int m = hash(hashIndex, capacity);
            int found = -1;
            while (m >= 0) {
                if (requestKey == key(m)) {
                    found = m;
                    break;
                }
                m = next(m);
            }
            if (found != -1) {
                //first remove currentKey from hashChain
                int toRemoveHash = (int) HashHelper.longHash(requestKey, hashCapacity);
                m = hash(toRemoveHash, capacity);
                if (m == found) {
                    setHash(toRemoveHash, next(m), capacity);
                } else {
                    while (m != -1) {
                        int next_of_m = next(m);
                        if (next_of_m == found) {
                            setNext(m, next(next_of_m));
                            break;
                        }
                        m = next_of_m;
                    }
                }
                final int lastIndex = mapSize - 1;
                if (lastIndex == found) {
                    //easy, was the last element
                    mapSize--;
                } else {
                    //less cool, we have to unchain the last value of the map
                    final long lastKey = key(lastIndex);
                    setKey(found, lastKey);
                    if (_withValue) {
                        setValue(found, value(lastIndex));
                    }
                    setNext(found, next(lastIndex));
                    int victimHash = (int) HashHelper.longHash(lastKey, hashCapacity);
                    m = hash(victimHash, capacity);
                    if (m == lastIndex) {
                        //the victim was the head of hashing list
                        setHash(victimHash, found, capacity);
                    } else {
                        //the victim is in the next, reChain it
                        while (m != -1) {
                            int next_of_m = next(m);
                            if (next_of_m == lastIndex) {
                                setNext(m, found);
                                break;
                            }
                            m = next_of_m;
                        }
                    }
                    mapSize--;
                }
            }
        }
    }*/

    /*
    public final boolean add(final long insertKey) {
        return put(insertKey, insertKey);
    }
    */

    public final boolean put(final long k1, final long k2, final long k3, final T insertValue) {
        if (keys == null) {
            reallocate(Constants.MAP_INITIAL_CAPACITY);
            setKey(0, k1, k2, k3);
            if (_withValue) {
                setValue(0, insertValue);
            }
            final int hashIndex = (int) ((k1 ^ k2 ^ k3) % (capacity * 2));
            setHash(hashIndex, 0, capacity);
            setNext(0, -1);
            mapSize++;
            return true;
        } else {
            long hashCapacity = capacity * 2;
            int insertKeyHash = (int) ((k1 ^ k2 ^ k3) % hashCapacity);
            int currentHash = hash(insertKeyHash, capacity);
            int m = currentHash;
            int found = -1;
            while (m >= 0) {
                if (k1 == keys[m * GROUP] && k2 == keys[m * GROUP + 1] && k3 == keys[m * GROUP + 2]) {
                    found = m;
                    break;
                }
                m = next(m);
            }
            if (found == -1) {
                final int lastIndex = mapSize;
                if (lastIndex == capacity) {
                    reallocate(capacity * 2);
                    hashCapacity = capacity * 2;
                    insertKeyHash = (int) ((k1 ^ k2 ^ k3) % hashCapacity);
                    currentHash = hash(insertKeyHash, capacity);
                }
                setKey(lastIndex, k1, k2, k3);
                if (_withValue) {
                    setValue(lastIndex, insertValue);
                }
                setHash((int) ((k1 ^ k2 ^ k3) % (capacity * 2)), lastIndex, capacity);
                setNext(lastIndex, currentHash);
                mapSize++;
                return true;
            } else {
                if (_withValue) {
                    if (value(found) != insertValue) {
                        setValue(found, insertValue);
                        return true;
                    }
                }
            }
            return false;
        }
    }

}
