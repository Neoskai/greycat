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

import greycat.Constants;
import greycat.Type;
import greycat.struct.Buffer;
import greycat.struct.LongArray;
import greycat.utility.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @ignore ts
 */
public class StorageValueChunk {

    private long world;
    private long time;
    private byte type;
    private Object value;
    private int index;

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
                        tuple.world = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        break;
                    case 1:
                        tuple.time = Base64.decodeToLongWithBounds(buffer, previous, cursor);
                        break;
                    case 2:
                        tuple.index = Base64.decodeToIntWithBounds(buffer, previous, cursor);
                        break;
                    case 3:
                        tuple.type = buffer.slice(previous,cursor)[0];
                        break;
                    case 4:
                        tuple.value= valueFromBuffer(buffer, previous, cursor, tuple.type);
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
                tuple.index = Base64.decodeToIntWithBounds(buffer, previous, cursor);
                break;
            case 3:
                tuple.type = buffer.slice(previous,cursor)[0];
                break;
            case 4:
                tuple.value= valueFromBuffer(buffer, previous, cursor, tuple.type);
                break;
        }
        return tuple;
    }

    /**
     * (INCOMPLETE) ONLY SUPPORT STRING OBJECTS
     * Deserialize Object from a byte array
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

    /**
     * (INCOMPLETE) ONLY SUPPORTS ALL PRIMITIVE TYPES
     * Rebuilds the object from the buffer, given the index of beginning and end of the object in the buffer
     * Object has to be written in Base64 format
     * @param buffer The complete buffer where the object is
     * @param begin First index of the object in buffer
     * @param end End index of the object in buffer
     * @param type The type of the Object
     * @return The builded object from buffer
     */
    public static Object valueFromBuffer(Buffer buffer, long begin, long end, byte type) {
        switch (type){
            case Type.STRING:
                return Base64.decodeToStringWithBounds(buffer,begin,end);
            case Type.BOOL:
                return buffer.slice(begin,end)[0] != 0;
            case Type.LONG:
                return Base64.decodeToLongWithBounds(buffer,begin,end);
            case Type.INT:
                return Base64.decodeToIntWithBounds(buffer, begin, end);
            case Type.DOUBLE:
                return Base64.decodeToDoubleWithBounds(buffer, begin, end);
            case Type.REMOVE:
                return null;
            default:
                return deserialize(buffer.slice(begin,end));
        }
    }

    /**
     * Deserialize an object
     * @param data The byte array containing the object
     * @return A java object containing the deserialized object
     */
    private static Object deserialize(byte[] data) {
        try{
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte type(){
        return type;
    }

    public Object value(){
        return value;
    }

    public long world(){
        return world;
    }

    public long time(){
        return time;
    }

    public int index(){
        return index;
    }

    @Override
    public String toString() {
        return "StorageValueChunk{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
