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
package greycat.utility.json.v3;

import greycat.struct.Buffer;

/**
 * @ignore ts
 */
public class Tokenizer {
    private static final byte FIELD_NAME  = 1;
    private static final byte FIELD_VALUE = 2;
    private static final byte OBJECT      = 3;
    private static final byte ARRAY       = 4;

    private int stateIndex = 0;
    private int cursor = 0;
    private int elementIndex = 0;
    private byte[] stateStack = new byte[256];

    public void parse(Buffer buffer, BufferIndex index){
        this.cursor     = 0;
        this.elementIndex = 0;
        this.stateIndex   = 0;
        this.stateStack[stateIndex] = FIELD_NAME;

        for(; cursor < buffer.length(); cursor++) {

            switch(buffer.read(cursor)) {
                case '{' :
                    setElement(index, elementIndex, JsonType.JSON_OBJECT_START, cursor);
                    elementIndex++; pushState(OBJECT);
                    setState(FIELD_NAME);
                    break;

                case '}' :
                    setElement(index, elementIndex, JsonType.JSON_OBJECT_END, cursor);
                    elementIndex++;
                    popState();
                    break;

                case '[' :
                    setElement(index, elementIndex, JsonType.JSON_ARRAY_START, cursor);
                    elementIndex++;
                    pushState(ARRAY);
                    setState(FIELD_VALUE);
                    break;

                case ']' :
                    setElement(index, elementIndex, JsonType.JSON_ARRAY_END, cursor);
                    elementIndex++;
                    popState();
                    break;

                case 'f' :
                    parseFalse(buffer, index);
                    elementIndex++;
                    break;

                case 't' :
                    parseTrue(buffer, index);
                    elementIndex++;
                    break;

                case '0'   :
                case '1'   :
                case '2'   :
                case '3'   :
                case '4'   :
                case '5'   :
                case '6'   :
                case '7'   :
                case '8'   :
                case '9'   :
                    parseNumberToken(buffer, index);
                    elementIndex++;
                    break;

                case '"' :
                    parseStringToken(buffer, index, elementIndex, cursor);
                    elementIndex++;
                    break;

                case ':' :
                    setState(FIELD_VALUE);
                    break;

                case ',' :
                    setState(this.stateStack[this.stateIndex-1] == ARRAY ? FIELD_VALUE : FIELD_NAME);
                    break;

            }

        }
        index.size = this.elementIndex;
    }


    private final int parseStringToken(Buffer buffer, BufferIndex index, int elementIndex, int position) {
        int tempPos = position;
        boolean containsEncodedChars = false;
        boolean endOfStringFound = false;
        while(!endOfStringFound) {
            tempPos++;
            switch(buffer.read(tempPos)) {
                case '"'  : { endOfStringFound = buffer.read(tempPos-1) != '\\'; break; }
                case '\\' : { containsEncodedChars = true; break; }
            }
        }

        if(this.stateStack[this.stateIndex-1] == OBJECT) {
            if(this.stateStack[this.stateIndex] == FIELD_NAME) {
                setElementData(index, elementIndex, JsonType.JSON_PROPERTY_NAME, position + 1, tempPos - position - 1);
            } else {
                if(containsEncodedChars){
                    setElementData(index, elementIndex, JsonType.JSON_PROPERTY_VALUE_STRING_ENC, position + 1, tempPos - position - 1);
                } else {
                    setElementData(index, elementIndex, JsonType.JSON_PROPERTY_VALUE_STRING, position + 1, tempPos - position - 1);
                }
            }
        } else {
            if(containsEncodedChars){
                setElementData(index, elementIndex, JsonType.JSON_ARRAY_VALUE_STRING_ENC, position + 1, tempPos - position - 1);
            } else {
                setElementData(index, elementIndex, JsonType.JSON_ARRAY_VALUE_STRING, position + 1, tempPos - position - 1);
            }
        }

        this.cursor = tempPos;
        return tempPos;
    }



    private boolean parseTrue(Buffer buffer, BufferIndex index) {
        if(
                buffer.read(this.cursor + 1) == 'r' &&
                        buffer.read(this.cursor + 2) == 'u' &&
                        buffer.read(this.cursor + 3) == 'e' )
        {
            if(this.stateStack[this.stateIndex-1] == OBJECT ) {
                setElementData(index, this.elementIndex, JsonType.JSON_PROPERTY_VALUE_BOOLEAN, this.cursor, 4);
            } else {
                setElementData(index, this.elementIndex, JsonType.JSON_ARRAY_VALUE_BOOLEAN, this.cursor, 4);
            }
            this.cursor += 3;
            return true;
        }
        return false;
    }

    private boolean parseFalse(Buffer buffer, BufferIndex index) {
        if(
                buffer.read(this.cursor + 1) == 'a' &&
                        buffer.read(this.cursor + 2) == 'l' &&
                        buffer.read(this.cursor + 3) == 's' &&
                        buffer.read(this.cursor + 4) == 'e' )
        {
            if(this.stateStack[this.stateIndex-1] == OBJECT ) {
                setElementData(index, this.elementIndex, JsonType.JSON_PROPERTY_VALUE_BOOLEAN, this.cursor, 5);
            } else {
                setElementData(index, this.elementIndex, JsonType.JSON_ARRAY_VALUE_BOOLEAN, this.cursor, 5);
            }
            this.cursor += 4;
            return true;
        }
        return false;
    }

    private void parseNumberToken(Buffer buffer, BufferIndex index) {
        int tempPos = this.cursor;
        boolean isEndOfNumberFound = false;
        while(!isEndOfNumberFound) {
            tempPos++;
            switch(buffer.read(tempPos)){
                case '0'   :
                case '1'   :
                case '2'   :
                case '3'   :
                case '4'   :
                case '5'   :
                case '6'   :
                case '7'   :
                case '8'   :
                case '9'   :
                case '.'   :  break;

                default    :  { isEndOfNumberFound = true; }
            }
        }
        if(this.stateStack[this.stateIndex-1] == OBJECT) {
            setElementData(index, this.elementIndex, JsonType.JSON_PROPERTY_VALUE_NUMBER, this.cursor, tempPos - this.cursor);
        } else {
            setElementData(index, this.elementIndex, JsonType.JSON_ARRAY_VALUE_NUMBER, this.cursor, tempPos - this.cursor);
        }
        this.cursor = tempPos -1;
    }

    private void setState(byte state){
        this.stateStack[this.stateIndex] = state;
    }

    private void pushState(byte state){
        this.stateStack[this.stateIndex] = state;
        this.stateIndex++;
    }
    private void popState() {
        this.stateIndex--;
    }

    private final void setElement(BufferIndex bIndex, int index, byte type, int position) {
        bIndex.type[index] = type;
        bIndex.start[index] = position;
        bIndex.length[index] = 1;
    }

    private final void setElementData(BufferIndex bIndex, int index, byte type, int position, int length) {
        bIndex.type[index] = type;
        bIndex.start[index] = position;
        bIndex.length[index] = length;
    }

}
