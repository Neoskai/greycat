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

package greycat.nsq;

import greycat.Constants;
import greycat.struct.Buffer;
import greycat.utility.Base64;

public class StorageKeyChunk {

    long world;
    long time;
    long id;
    int index;

    static StorageKeyChunk build(Buffer buffer) {
        StorageKeyChunk tuple = new StorageKeyChunk();
        long cursor = 0;
        long length = buffer.length();
        long previous = 0;
        int index = 0;
        while (cursor < length) {
            byte current = buffer.read(cursor);
            if (current == Constants.KEY_SEP) {
                switch (index) {
                    case 0:
                        tuple.world = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        break;
                    case 1:
                        tuple.time = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        break;
                    case 2:
                        tuple.id = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        break;
                    case 3:
                        tuple.index= Base64.decodeToIntWithBounds(buffer, previous, cursor);
                        break;
                }
                index++;
                previous = cursor + 1;
            }
            cursor++;
        }
        //collect last
        switch (index) {
            case 0:
                tuple.world = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                break;
            case 1:
                tuple.time = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                break;
            case 2:
                tuple.id = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                break;
            case 3:
                tuple.index= Base64.decodeToIntWithBounds(buffer, previous, cursor);
                break;
        }
        return tuple;
    }

    @Override
    public String toString() {
        return "StorageKeyChunk{" +
                "world=" + world +
                ", time=" + time +
                ", id=" + id +
                ", index=" + index +
                '}';
    }
}

