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
import greycat.Type;
import greycat.struct.Buffer;

public class StorageValueChunk {

    byte type;
    Object value;

    public static StorageValueChunk build(Buffer buffer){
        StorageValueChunk tuple = new StorageValueChunk();
        long cursor = 0;
        long length = buffer.length();
        long previous = 0;
        int index = 0;
        while (cursor < length) {
            byte current = buffer.read(cursor);
            if (current == Constants.CHUNK_SEP) {
                switch (index) {
                    case 0:
                        tuple.type = buffer.slice(previous,cursor)[0];
                        break;
                    case 1:
                        tuple.value= deserialize(buffer.slice(previous,cursor), tuple.type);
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
                tuple.type = buffer.slice(previous,cursor)[0];
                break;
            case 1:
                tuple.value= deserialize(buffer.slice(previous,cursor-1), tuple.type);
                break;
        }
        return tuple;
    }

    /**
     * Deserialize Object
     * @param bytes Object bytes
     * @return Deserialized object
     */
    public static Object deserialize(byte[] bytes, byte type)  {
        switch (type){
            case Type.STRING :
                return new String(bytes);
        }

        return null;
    }

    @Override
    public String toString() {
        return "StorageValueChunk{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
