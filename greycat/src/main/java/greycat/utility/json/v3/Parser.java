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

import greycat.*;
import greycat.base.BaseNode;
import greycat.struct.Buffer;
import greycat.utility.json.v2.TypedObject;

import java.util.*;

public class Parser {

    private byte RECORD_BUILD = 1;
    private byte NODE_BUILD = 2;
    private byte VALUE_BUILD = 3;
    private byte INDEX_BUILD = 4;

    private byte TIMELIST_BUILD = 5;

    private Graph _graph;

    public Parser(Graph g){
        _graph = g;
    }

    public void parse(Buffer buffer){
        // Tokenize the buffer
        BufferIndex index = new BufferIndex((int) buffer.length());
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.parse(buffer,index);

        String currentProperty = "";
        Map<String, Object> properties = new HashMap<>();

        byte state = -1;

        LinkedList<TypedObject> parents = new LinkedList<>();
        List<Object> objectList = new ArrayList<>();

        int stack = 0;

        for(int i = 0; i < index.size; i++){
            int type = index.type[i];
            int length = index.length[i];
            int start = index.start[i];

            switch(type){
                case JsonType.JSON_OBJECT_START:
                    stack++;
                    switch(stack){
                        case 1:
                            state = RECORD_BUILD;
                            break;
                        case 2:
                            state = NODE_BUILD;
                            break;
                    }
                    break;

                case JsonType.JSON_ARRAY_START:
                    stack++;
                    break;

                case JsonType.JSON_OBJECT_END:
                    stack--;
                    break;

                case JsonType.JSON_ARRAY_END:
                    stack--;
                    if (state == TIMELIST_BUILD){
                        state = NODE_BUILD;
                        System.out.println("----------- Creation -------------");
                        // We have enough information to build the node, so we build it and put it in the stack
                        _graph.connect(null);
                        _graph.lookup((long) properties.get("world"), (long) objectList.get(0), (long) properties.get("id"), new Callback<Node>() {
                            @Override
                            public void on(Node result) {
                                if (result == null){
                                    // @TODO : Use nodetype to create an instance of the good type
                                    Node newNode = new BaseNode((long) properties.get("world"), (long) objectList.get(0), (long) properties.get("id"), _graph);
                                    _graph.resolver().initNode(newNode, Constants.NULL_LONG);

                                    parents.push(new TypedObject(Type.NODE, newNode));

                                } else {
                                    parents.push(new TypedObject(Type.NODE, result));
                                }
                            }
                        });
                        _graph.disconnect(null);
                    }
                    break;

                case JsonType.JSON_PROPERTY_NAME:
                    currentProperty = new String(buffer.slice(start,start+length-1));

                    if("times".equals(currentProperty))
                        state = TIMELIST_BUILD;
                    if("values".equals(currentProperty))
                        state = VALUE_BUILD;
                    break;

                case JsonType.JSON_ARRAY_VALUE_NUMBER:
                    if(state == TIMELIST_BUILD)
                        objectList.add(Long.parseLong(new String(buffer.slice(start,start+length-1))));

                    break;
                case JsonType.JSON_ARRAY_VALUE_STRING:
                    break;
                case JsonType.JSON_ARRAY_VALUE_STRING_ENC:
                    break;
                case JsonType.JSON_ARRAY_VALUE_BOOLEAN:
                    break;

                case JsonType.JSON_PROPERTY_VALUE_STRING:
                    properties.put(currentProperty, new String(buffer.slice(start, start+length-1)));
                    break;
                case JsonType.JSON_PROPERTY_VALUE_STRING_ENC:
                    properties.put(currentProperty, new String(buffer.slice(start, start+length-1)));
                    break;
                case JsonType.JSON_PROPERTY_VALUE_NUMBER:
                    properties.put(currentProperty, Long.parseLong(new String(buffer.slice(start, start+length-1))));
                    break;
                case JsonType.JSON_PROPERTY_VALUE_BOOLEAN:
                    properties.put(currentProperty, Boolean.valueOf(new String(buffer.slice(start, start+length-1))));
                    break;
            }
        }
    }
}
