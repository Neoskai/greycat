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

/**
 * Created by assaad on 02/05/2017.
 */
public interface NDHashManager {

    /**
     * Create a new element in the hashtable
     **/
    long createNewElement();


    /**
     * Update the element in the hashtable
     *
     * @param id                the id of the resolved element from the hashtable
     * @param key               the multi dimensional key being inserted
     * @param value             the object being indexed/profiled for this key
     */
    void update(long id, double[] key, long value);


    /**
     * Resolve the current object of the hashed id 
     * @param id
     * @return
     */
    Object get(long id);


}
