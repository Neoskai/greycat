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
package greycat.utility.json;

import greycat.*;
import greycat.base.BaseNode;
import greycat.struct.Buffer;

import java.util.*;

import static greycat.utility.json.JsonConst.*;


public class JsonParserV2 {
    private Stack _list;
    private Graph _graph;

    private Node _current;
    private Map<String, Object> _values;

    public JsonParserV2(Graph g){
        _list = new Stack();
        _graph = g;
        _current = null;
        _values = new HashMap<>();
    }

    public void parse(Buffer buffer){
        long cursor = 0;

        long end = buffer.length();

        while(cursor != end){
            switch(buffer.read(cursor)){
                case OBJS:
                    _list.push(OBJS);
                    cursor = parseRecord(buffer, cursor+1);
                    break;

                case OBJE:
                    _list.pop();
                    break;

                case ARRS:
                    _list.push(ARRS);
                    break;

                case ARRE:
                    _list.pop();
                    break;
            }
            cursor++;
        }
        System.out.println("Ended with size: " + _list.size());
    }

    public long parseRecord(Buffer buffer, long start){
        long cursor = start;
        long end = buffer.length();

        Map<Integer,Long> startLevel= new HashMap<>();
        startLevel.put(_list.size(), start); // Putting start of first element

        boolean isLast = false;

        while(cursor != end){
            switch(buffer.read(cursor)){
                case OBJS:
                    isLast= false;
                    startLevel.put(_list.size(), cursor+1);
                    _list.push(OBJS);
                    break;

                case OBJE:
                    if(_list.size() == 2) {
                        buildValue(buffer,startLevel.get(_list.size()), cursor-1);
                        isLast = true;
                    }
                    _list.pop();
                    if(_list.size() ==1){ // If we finished to parse the record
                        return cursor;
                    }
                    break;

                case ARRS:
                    _list.push(ARRS);
                    startLevel.put(_list.size(), cursor+1);
                    break;

                case ARRE:
                    _list.pop();
                    break;
                case SEP:
                    if(_list.size() == 2 && !isLast) {
                        buildValue(buffer,startLevel.get(_list.size()), cursor);
                    }
                    startLevel.put(_list.size(), cursor+1);
                    break;
            }
            cursor++;
        }
        return start;
    }

    public void buildValue(Buffer buffer, long start, long end){
        System.out.println("Building value for: " + new String(buffer.slice(start,end)));

        long cursor = start;

        while(buffer.read(cursor) != TEXT){
            cursor++;
        }
        cursor++;
        long startKey = cursor;
        while(buffer.read(cursor) != TEXT){
            cursor++;
        }
        String key = new String(buffer.slice(startKey,cursor-1));
        cursor++;

        cursor++;

        switch(key){
            case "world":
                long world = Long.parseLong(new String(buffer.slice(cursor,end-1)));
                _values.put("world", world);
                break;

            case "id":
                long id = Long.parseLong(new String(buffer.slice(cursor,end-1)));
                _values.put("id", id);
                break;

            case "nodetype":
                String nodetype = new String(buffer.slice(cursor+1, end-2));
                _values.put("nodetype", nodetype);
                break;

            case "times":
                List<Long> timeList = new ArrayList<>();
                cursor++; // Post array opening
                long temStart = cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        timeList.add(Long.parseLong(new String(buffer.slice(temStart, cursor-1))));
                        cursor++;
                        temStart = cursor;
                    } else {
                        cursor++;
                    }
                }
                if(temStart+1 < cursor){
                    timeList.add(Long.parseLong(new String(buffer.slice(temStart, cursor-1))));
                }
                _values.put("times", timeList);
                break;

            case "values":
                int stack = 0;
                int currentTime = -1;
                long temVStart = 0;

                List<Long> times = (List<Long>) _values.get("times");

                while(cursor != end){
                    switch(buffer.read(cursor)){
                        case ARRS:
                            stack++;
                            if(stack == 2){
                                temVStart= cursor;
                                currentTime++;
                            }
                            break;
                        case ARRE:
                            if(stack == 2){ // End of an array, so of a node
                                cursor = getNode(buffer,temVStart+1);

                                _graph.lookup((long) _values.get("world"), times.get(currentTime), (long) _values.get("id"), new Callback<Node>() {
                                    @Override
                                    public void on(Node result) {
                                        if (result == null){
                                            // @TODO : Use nodetype to create an instance of the good type
                                            Node newNode = new BaseNode((long) _values.get("world"), times.get(0), (long) _values.get("id"), _graph);
                                            _graph.resolver().initNode(newNode, Constants.NULL_LONG);
                                        }

                                        // Set all the values in the node
                                    }
                                });
                            }
                            stack--;
                            break;
                    }
                    cursor++;
                }
                break;

            default: // We are reading an index
                break;
        }

    }

    public long getNode(Buffer buffer, long start){
        // 21,{"value":[5,22.5], "value2":[4,azeaze]}
        long cursor = start;

        while (buffer.read(cursor) != SEP){
            cursor++;
        }
        int type = Integer.parseInt(new String(buffer.slice(start,cursor-1)));

        int stack = 0;
        while(buffer.read(cursor) != OBJS){
            cursor++;
        }
        stack++;
        cursor++;

        long valueStart = cursor;
        boolean isFirstText = true;

        String currentName;
        Object currentObject;

        while(buffer.read(cursor) != OBJE){
            switch(buffer.read(cursor)){
                case SEP:
                    if(stack == 1){
                        isFirstText = true;
                        currentObject= getObject(buffer,valueStart,cursor-1);
                        // Seek node and set value
                    }
                    break;
                case TEXT:
                    if(isFirstText) {
                        valueStart = ++cursor;
                        isFirstText = false;
                    } else {
                        currentName = new String(buffer.slice(valueStart,cursor-1));
                    }
                    break;
                case ARRS:
                    stack++;
                    break;

                case ARRE:
                    stack--;
                    break;
            }

            cursor++;
        }
        // Retrieve last object
        currentObject= getObject(buffer,valueStart,cursor-1);
        //Seek node and set value

        stack--;

        return cursor;
    }

    /**
     * @TODO
     * General architecture is not correct, just check algorithm part
     */
    private Object getObject(Buffer buffer, long start, long end ){
        long cursor = start;

        while (buffer.read(cursor) != SEP){
            cursor++;
        }
        int type = Integer.parseInt(new String(buffer.slice(start,cursor-1)));

        cursor++;

        long startValue = cursor;

        switch(type){
            case Type.BOOL:
                while(buffer.read(cursor) != OBJE){
                    cursor++;
                }
                return Boolean.valueOf(new String(buffer.slice(startValue, cursor-1)));

            case Type.STRING:
                while(buffer.read(cursor) != TEXT){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != TEXT){
                    cursor++;
                }
                return new String(buffer.slice(startValue, cursor-1));

            case Type.LONG:
                while(buffer.read(cursor) != OBJE){
                    cursor++;
                }
                return Long.parseLong(new String(buffer.slice(startValue, cursor-1)));

            case Type.INT:
                while(buffer.read(cursor) != OBJE){
                    cursor++;
                }
                return Integer.parseInt(new String(buffer.slice(startValue, cursor-1)));

            case Type.DOUBLE:
                while(buffer.read(cursor) != OBJE){
                    cursor++;
                }
                return Double.parseDouble(new String(buffer.slice(startValue, cursor-1)));


            case Type.DOUBLE_ARRAY:
                List<Double> doubleList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        doubleList.add(Double.parseDouble(new String(buffer.slice(startValue,cursor-1))));
                        startValue = cursor+1;
                    }
                    cursor++;
                }
                //Retrieve last element
                doubleList.add(Double.parseDouble(new String(buffer.slice(startValue,cursor-1))));

                return doubleList;

            case Type.LONG_ARRAY:
                List<Long> longList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        longList.add(Long.parseLong(new String(buffer.slice(startValue,cursor-1))));
                        startValue = cursor+1;
                    }
                    cursor++;
                }
                //Retrieve last element
                longList.add(Long.parseLong(new String(buffer.slice(startValue,cursor-1))));

                return longList;


            case Type.INT_ARRAY:
                List<Integer> intList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        intList.add(Integer.parseInt(new String(buffer.slice(startValue,cursor-1))));
                        startValue = cursor+1;
                    }
                    cursor++;
                }
                //Retrieve last element
                intList.add(Integer.parseInt(new String(buffer.slice(startValue,cursor-1))));

                return intList;

            case Type.STRING_ARRAY:
                List<String> stringList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                boolean isText = true;
                cursor++;
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE && isText){
                    if(buffer.read(cursor) == SEP){
                        stringList.add(new String(buffer.slice(startValue,cursor-2)));
                        startValue = ++cursor;
                    }
                    if(buffer.read(cursor) == TEXT){
                        isText = !isText;
                    }
                    cursor++;
                }
                //Retrieve last element
                stringList.add(new String(buffer.slice(startValue,cursor-2)));

                return stringList;

            case Type.LONG_TO_LONG_MAP:
                Map<Long,Long> llMap = new HashMap<>();
                while(buffer.read(cursor) != OBJS){
                    cursor++;
                }
                cursor++;
                startValue = ++cursor;

                String currentLLName = "";

                while(buffer.read(cursor) != OBJE){
                    switch(buffer.read(cursor)){
                        case COR:
                            currentLLName = new String(buffer.slice(startValue, cursor-2));
                            startValue = cursor+1;
                            break;
                        case SEP:
                            llMap.put(Long.parseLong(currentLLName), Long.parseLong(new String(buffer.slice(startValue, cursor-1))));
                            startValue=cursor+2;
                            break;
                    }
                    cursor++;
                }
                llMap.put(Long.parseLong(currentLLName), Long.parseLong(new String(buffer.slice(startValue, cursor-1))));

                return llMap;

            case Type.LONG_TO_LONG_ARRAY_MAP:
                break;

            case Type.STRING_TO_INT_MAP:
                Map<String, Integer> siMap = new HashMap<>();
                while(buffer.read(cursor) != OBJS){
                    cursor++;
                }
                cursor++;
                startValue = ++cursor;

                String currentSIName = "";

                while(buffer.read(cursor) != OBJE){
                    switch(buffer.read(cursor)){
                        case COR:
                            currentSIName = new String(buffer.slice(startValue, cursor-2));
                            startValue = cursor+1;
                            break;
                        case SEP:
                            siMap.put(currentSIName, Integer.parseInt(new String(buffer.slice(startValue, cursor-1))));
                            startValue=cursor+2;
                            break;
                    }
                    cursor++;
                }
                siMap.put(currentSIName, Integer.parseInt(new String(buffer.slice(startValue, cursor-1))));

                return siMap;


            case Type.RELATION:
                break;


            case Type.DMATRIX:
                break;

            case Type.LMATRIX:
                break;


            case Type.ESTRUCT:
                break;

            case Type.ESTRUCT_ARRAY:
                break;

            case Type.ERELATION:
                break;


            case Type.TASK:
                break;

            case Type.TASK_ARRAY:
                break;

            case Type.NODE:
                break;

            case Type.INT_TO_INT_MAP:
                Map<Integer, Integer> iiMap = new HashMap<>();
                while(buffer.read(cursor) != OBJS){
                    cursor++;
                }
                cursor++;
                startValue = ++cursor;

                String currentIIName = "";

                while(buffer.read(cursor) != OBJE){
                    switch(buffer.read(cursor)){
                        case COR:
                            currentIIName = new String(buffer.slice(startValue, cursor-2));
                            startValue = cursor+1;
                            break;
                        case SEP:
                            iiMap.put(Integer.parseInt(currentIIName), Integer.parseInt(new String(buffer.slice(startValue, cursor-1))));
                            startValue=cursor+2;
                            break;
                    }
                    cursor++;
                }
                iiMap.put(Integer.parseInt(currentIIName), Integer.parseInt(new String(buffer.slice(startValue, cursor-1))));

                return iiMap;

            case Type.INT_TO_STRING_MAP:
                Map<Integer, String> isMap = new HashMap<>();
                while(buffer.read(cursor) != OBJS){
                    cursor++;
                }
                cursor++;
                startValue = ++cursor;

                String currentISName = "";

                while(buffer.read(cursor) != OBJE){
                    switch(buffer.read(cursor)){
                        case COR:
                            currentISName = new String(buffer.slice(startValue, cursor-2));
                            startValue = cursor+2;
                            break;
                        case SEP:
                            isMap.put(Integer.parseInt(currentISName), new String(buffer.slice(startValue, cursor-2)));
                            startValue=cursor+2;
                            break;
                    }
                    cursor++;
                }
                isMap.put(Integer.parseInt(currentISName), new String(buffer.slice(startValue, cursor-2)));

                return isMap;

            case Type.INDEX:
                break;

            case Type.KDTREE:
                break;

            case Type.NDTREE:
                break;
        }

        return null;
    }
}
