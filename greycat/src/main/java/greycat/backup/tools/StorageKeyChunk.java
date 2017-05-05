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
import greycat.struct.Buffer;
import greycat.utility.Base64;

public class StorageKeyChunk {

    private long world;
    private long time;
    private long id;
    private String index;
    private long eventId;

    /**
     * Builds a StorageKeyChunk from it's default representation in a buffer
     * @param buffer The buffer containing the key
     * @return The key
     */
    public static StorageKeyChunk build(Buffer buffer) {
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
                        tuple.index= Base64.decodeToStringWithBounds(buffer, previous, cursor);
                        break;
                    case 4:
                        tuple.eventId= Base64.decodeToLongWithBounds(buffer, previous, cursor);
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
                tuple.index= Base64.decodeToStringWithBounds(buffer, previous, cursor);
                break;
            case 4:
                tuple.eventId= Base64.decodeToLongWithBounds(buffer, previous, cursor);
                break;
        }
        return tuple;
    }

    /**
     * Rebuild a StorageKeyChunk from a String
     * @param buffer buffer containing the string
     * @return The builded StorageKeyChunk
     */
    public static StorageKeyChunk buildFromString(Buffer buffer){
        StorageKeyChunk tuple = new StorageKeyChunk();

        String fullKey = new String(buffer.data());
        String[] keys = fullKey.split(";");

        int index = 0;

        while (index < keys.length) {
            switch (index) {
                case 0:
                    tuple.world = Long.parseLong(keys[index]);
                    index++;
                    break;
                case 1:
                    tuple.time = Long.parseLong(keys[index]);
                    index++;
                    break;
                case 2:
                    tuple.id = Long.parseLong(keys[index]);
                    index++;
                    break;
                case 3:
                    tuple.index= keys[index];
                    index++;
                    break;
                case 4:
                    tuple.eventId = Long.parseLong(keys[index]);
                    index++;
                    break;
            }
        }


        return tuple;
    }

    /**
     * Builds the string that represents a minimal representation of the StorageKeyChunk
     * @return String containing the key
     */
    public String buildString(){
        String key = "";
        key += world
                + ";"
                + time
                + ";"
                + id
                + ";"
                + index
                +";"
                +eventId;

        return key;
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

    public long id(){
        return id;
    }

    public String index(){
        return index;
    }

    public long world(){
        return world;
    }

    public long time(){
        return time;
    }

    public long eventId() {
        return eventId;
    }
}

