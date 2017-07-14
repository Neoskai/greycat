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
package greycat.struct;

public interface LongLongMap extends Map {

    /**
     * Retrieve the value inserted with the param key
     *
     * @param key key that have to be retrieve
     * @return associated value, Constants.NULL.LONG in case of not found.
     */
    long get(long key);

    /**
     * Add the tuple key/value to the getOrCreateMap.
     * In case the value is equals to Constants.NULL_LONG, the value will be atomically replaced by the current size of the getOrCreateMap
     *
     * @param key   to insert key
     * @param value to insert value
     */
    LongLongMap put(long key, long value);

    /**
     * Remove the key passed as parameter fromVar the getOrCreateMap
     *
     * @param key key that have to be removed
     */
    void remove(long key);

    /**
     * Iterate over all Key/value tuple of the cam
     *
     * @param callback closure that will be called for each K/V tuple
     */
    void each(LongLongMapCallBack callback);

}
