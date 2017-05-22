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

package greycat.backup.tools;

import greycat.Callback;
import greycat.struct.Buffer;
import greycat.struct.BufferIterator;

/**
 * @ignore ts
 */
public class StorageHandler {
    private static final int POOLSIZE = 5;

    private SparkeyBackupStorage[] _storages; // Contains all the storage managers

    public StorageHandler(){
        _storages = new SparkeyBackupStorage[POOLSIZE];
    }

    /**
     * Initialization of the storage handler and all the storages
     */
    public void load(){
        for(int i = 0; i < POOLSIZE; i++){
            _storages[i] = new SparkeyBackupStorage(i);

            _storages[i].connect(new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    // Nothing
                }
            });
        }
    }

    /**
     * Handles a buffer input by forwarding it to the good storage
     * @param buffer The buffer to process
     * @throws Exception Errors during the process
     */
    public void process(Buffer buffer) throws Exception{
        BufferIterator iterator = buffer.iterator();
        Buffer keyBuffer = iterator.next();

        StorageKeyChunk keyChunk = StorageKeyChunk.build(keyBuffer);
        int currentPool = (int) keyChunk.id()%POOLSIZE;

        // Storing
        synchronized (_storages[currentPool]) {
            _storages[currentPool].put(buffer, new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    buffer.free();
                }
            });
        }
    }

    /**
     * Closes all the storages
     */
    public void close(){
        for(int i = 0; i < POOLSIZE; i++){
            _storages[i].disconnect(new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    // Nothing
                }
            });
        }
    }
}
