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
package greycat.utility.json.v2;

import greycat.*;
import greycat.base.BaseNode;
import greycat.struct.*;

import java.util.*;
import java.util.Map;

import static greycat.utility.json.JsonConst.*;

/**
 * @ignore ts
 */
public class JsonParserV2 {
    private Stack _list;
    private Graph _graph;

    private Map<String, Object> _values;

    private LinkedList<TypedObject> stackList;

    public JsonParserV2(Graph g){
        _list = new Stack();
        _graph = g;
        _values = new HashMap<>();
        stackList = new LinkedList<>();
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
    }

    public long parseRecord(Buffer buffer, long start){
        long cursor = start;
        long end = buffer.length();

        long tempStart = start;

        while(cursor != end){
            switch(buffer.read(cursor)){
                case OBJS:
                    _list.push(OBJS);
                    break;

                case OBJE:
                    _list.pop();
                    if(_list.size() ==1){ // If we finished to parse the record
                        // Build last value in record
                        buildValue(buffer,tempStart,cursor-1);
                        return cursor;
                    }
                    break;

                case ARRS:
                    _list.push(ARRS);
                    break;

                case ARRE:
                    _list.pop();
                    break;
                case SEP:
                    if(_list.size() == 2) {
                        buildValue(buffer, tempStart, cursor-1);
                        tempStart = cursor+1;
                    }
                    break;
            }
            cursor++;
        }

        stackList.pop(); // Once the node is loaded, we don't need it in the stack anymore
        return start;
    }

    public void buildValue(Buffer buffer, long start, long end){
        System.out.println("BuildValue: " + new String(buffer.slice(start,end)));
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
                long world = Long.parseLong(new String(buffer.slice(cursor,end)));
                _values.put("world", world);
                break;

            case "id":
                long id = Long.parseLong(new String(buffer.slice(cursor,end)));
                _values.put("id", id);
                break;

            case "nodetype":
                String nodetype = new String(buffer.slice(cursor+1, end-1));
                _values.put("nodetype", nodetype);
                break;

            case "time":
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
                if(temStart < cursor){
                    timeList.add(Long.parseLong(new String(buffer.slice(temStart, cursor-1))));
                }
                _values.put("times", timeList);

                // We have enough information to build the node, so we build it and put it in the stack
                _graph.connect(null);
                _graph.lookup((long) _values.get("world"), timeList.get(0), (long) _values.get("id"), new Callback<Node>() {
                    @Override
                    public void on(Node result) {
                        if (result == null){
                            // @TODO : Use nodetype to create an instance of the good type
                            Node newNode = new BaseNode((long) _values.get("world"), timeList.get(0), (long) _values.get("id"), _graph);
                            _graph.resolver().initNode(newNode, Constants.NULL_LONG);

                            stackList.push(new TypedObject(Type.NODE, newNode));

                        } else {
                            stackList.push(new TypedObject(Type.NODE, result));
                        }
                    }
                });
                _graph.disconnect(null);
                break;

            //case "value":
            case "values":
                int stack = 0;
                int currentTime = -1;
                long temVStart = 0;

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
                            if(stack == 2){ // End of an array, so of the values of a node at a given Time
                                getNode(buffer,temVStart+1, cursor-1, currentTime);
                            }
                            stack--;
                            break;
                    }
                    cursor++;
                }
                break;
            default:
                break;
        }
    }

    public long getNode(Buffer buffer, long start, long end, int currentTime){
        System.out.println("Getting node: " + new String(buffer.slice(start,end)));

        long cursor = start;

        while (buffer.read(cursor) != SEP){
            cursor++;
        }
        int type = Integer.parseInt(new String(buffer.slice(start,cursor-1)));
        // Check if type is different from Node
        if(type != Type.NODE){
            System.err.println("Expecting node, but received: " + type);
        }

        int stack = 0;
        while(buffer.read(cursor) != OBJS){
            cursor++;
        }
        cursor++;
        stack++;

        long valueStart = cursor;
        boolean isFirstText = true;

        String currentName = "";
        TypedObject currentObject= null;

        while(cursor != end && stack > 0){
            switch(buffer.read(cursor)){
                case SEP:
                    if(stack == 2){
                        isFirstText = true;
                    }
                    break;

                case TEXT:
                    if(stack == 1) {
                        if (isFirstText) {
                            valueStart = cursor + 1;
                            isFirstText = false;
                        } else {
                            currentName = new String(buffer.slice(valueStart, cursor - 1));}
                    }
                    break;


                case ARRS:
                    stack++;
                    if(stack == 2){
                        valueStart= cursor+1;
                    }
                    break;

                case ARRE:
                    stack--;
                    if(stack ==2){
                        currentObject= getObject(buffer,valueStart,cursor-1);

                        if(currentObject != null) {
                            _graph.connect(null);
                            List<Long> times = (List<Long>) _values.get("times");
                            TypedObject finalCurrentObject = currentObject;
                            String finalCurrentName = currentName;

                            if (stackList.peek().getType() == Type.NODE) {
                                Node n = (Node) stackList.peek().getObject();
                                n.travelInTime(times.get(currentTime), new Callback<Node>() {
                                    @Override
                                    public void on(Node result) {
                                        if (finalCurrentObject != null) {
                                            if (isComplexType(finalCurrentObject.getType())) {
                                                Object obj = result.getOrCreate(finalCurrentName, finalCurrentObject.getType());
                                                obj = initObject(obj, finalCurrentObject);
                                                result.set(finalCurrentName, finalCurrentObject.getType(), obj);
                                            } else {
                                                result.set(finalCurrentName, finalCurrentObject.getType(), finalCurrentObject.getObject());
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                    break;

                case OBJS:
                    stack++;
                    break;

                case OBJE:
                    stack--;
                    break;
            }

            cursor++;
        }

        return cursor;
    }

    /**
     * @TODO
     * General architecture is not correct, just check algorithm part
     */
    private TypedObject getObject(Buffer buffer, long start, long end ){
        System.out.println("Getting object: " + new String(buffer.slice(start,end)));
        if(start == end+1){ // Object is empty
            return null;
        }

        long cursor = start;

        while (buffer.read(cursor) != SEP){
            cursor++;
        }
        int type = Integer.parseInt(new String(buffer.slice(start,cursor-1)));

        cursor++;

        long startValue = cursor;

        switch(type){
            case Type.BOOL:
                while(buffer.read(cursor) != OBJE && buffer.read(cursor) != ARRE){
                    cursor++;
                }
                return new TypedObject(Type.BOOL,Boolean.valueOf(new String(buffer.slice(startValue, cursor-1))));

            case Type.STRING:
                while(buffer.read(cursor) != TEXT){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != TEXT){
                    cursor++;
                }
                return new TypedObject(Type.STRING, new String(buffer.slice(startValue, cursor-1)));

            case Type.LONG:
                while(buffer.read(cursor) != OBJE && buffer.read(cursor) != ARRE){
                    cursor++;
                }
                return new TypedObject(Type.LONG, Long.parseLong(new String(buffer.slice(startValue, cursor-1))));

            case Type.INT:
                while(buffer.read(cursor) != OBJE && buffer.read(cursor) != ARRE){
                    cursor++;
                }
                return new TypedObject(Type.INT, Integer.parseInt(new String(buffer.slice(startValue, cursor-1))));

            case Type.DOUBLE:
                while(buffer.read(cursor) != OBJE && buffer.read(cursor) != ARRE){
                    cursor++;
                }
                return new TypedObject(Type.DOUBLE, Double.parseDouble(new String(buffer.slice(startValue, cursor-1))));

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

                return new TypedObject(Type.DOUBLE_ARRAY,doubleList);

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

                return new TypedObject(Type.LONG_ARRAY, longList);


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

                return new TypedObject(Type.INT_ARRAY, intList);

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

                return new TypedObject(Type.STRING_ARRAY, stringList);

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

                return new TypedObject(Type.LONG_TO_LONG_MAP, llMap);

            case Type.LONG_TO_LONG_ARRAY_MAP:
                Map<Long, List<Long>> llaMap = new HashMap<>();

                while(buffer.read(cursor) != OBJS){
                    cursor++;
                }
                cursor++;
                boolean isFirst = true;
                long startLLA = 0;

                long currentLValue = -1;
                int stack = 0;
                List<Long> llaList = new ArrayList<>();
                while(cursor != end){
                    switch(buffer.read(cursor)){
                        case TEXT:
                            if(isFirst){
                                startLLA = cursor+1;
                                isFirst = false;
                            } else {
                                isFirst = true;
                                currentLValue = Long.parseLong(new String(buffer.slice(startLLA, cursor-1)));
                            }
                            break;

                        case ARRS:
                            stack++;
                            startLLA=cursor+1;
                            break;

                        case SEP:
                            if(stack == 1){
                                llaList.add(Long.parseLong(new String(buffer.slice(startLLA, cursor-1))));
                            }
                            break;

                        case ARRE:
                            if(stack == 1){
                                llaList.add(Long.parseLong(new String(buffer.slice(startLLA, cursor-1))));
                                llaMap.put(currentLValue, llaList);
                                llaList.clear();
                            }
                            stack--;
                            break;

                        case OBJE:
                            return new TypedObject(Type.LONG_TO_LONG_ARRAY_MAP, llaMap);
                    }
                    cursor++;
                }
                return new TypedObject(Type.LONG_TO_LONG_ARRAY_MAP, llaMap);

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

                return new TypedObject(Type.STRING_TO_INT_MAP, siMap);


            case Type.RELATION:
                List<Long> relationList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        relationList.add(Long.parseLong(new String(buffer.slice(startValue,cursor-1))));
                        startValue = cursor+1;
                    }
                    cursor++;
                }
                //Retrieve last element
                relationList.add(Long.parseLong(new String(buffer.slice(startValue,cursor-1))));

                return new TypedObject(Type.RELATION, relationList);


            case Type.DMATRIX:
                List<Double> dMatList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        dMatList.add(Double.parseDouble(new String(buffer.slice(startValue,cursor-1))));
                        startValue = cursor+1;
                    }
                    cursor++;
                }
                //Retrieve last element
                dMatList.add(Double.parseDouble(new String(buffer.slice(startValue,cursor-1))));

                return new TypedObject(Type.DMATRIX,dMatList);

            case Type.LMATRIX:
                List<Long> lMatList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE){
                    if(buffer.read(cursor) == SEP){
                        lMatList.add(Long.parseLong(new String(buffer.slice(startValue,cursor-1))));
                        startValue = cursor+1;
                    }
                    cursor++;
                }
                //Retrieve last element
                lMatList.add(Long.parseLong(new String(buffer.slice(startValue,cursor-1))));

                return new TypedObject(Type.LMATRIX,lMatList);


            case Type.ESTRUCT:
                //@TODO
                break;

            case Type.ESTRUCT_ARRAY:
                System.out.println("Parse EstructArray: " + new String(buffer.slice(startValue, end)));
                Map<String,TypedObject> elements = new HashMap<>();
                int eaStack = 0;

                String eaName = "";
                long eaIndex = -1;
                while(cursor != end){
                    switch(buffer.read(cursor)){
                        case OBJS:
                            eaIndex = cursor+1;
                            break;

                        case COR:
                            eaName = new String(buffer.slice(eaIndex,cursor-1));
                            eaName = eaName.replace("\"", "");
                            eaIndex = cursor+1;
                            break;

                        case OBJE:
                            // Add the element to the list of elements
                            elements.put(eaName, getObject(buffer, eaIndex+1, cursor));
                            break;
                        case ARRS:
                            eaStack++;
                            break;

                        case ARRE:
                            eaStack--;
                            if(eaStack == 0){
                                return new TypedObject(Type.ESTRUCT_ARRAY, elements);
                            } else if (eaStack == 1){ // Array of Estruct
                                // Last Estruct to Set
                            } else if (eaStack == 2){ // Array of Values
                                // Last value to set
                            }
                            break;
                        case SEP:
                            if(eaStack == 1){ // Array of Estruct Level
                                elements.clear();
                                // We now have all the values for an Estruct, We need to set it, and switch to the new estruct
                            } else if (eaStack == 2){ // Array of Values Level
                                elements.put(eaName, getObject(buffer, eaIndex+1, cursor));
                                eaIndex = cursor+1;
                            }
                            break;
                    }

                    cursor++;
                }
                break;

            case Type.ERELATION:
                //@TODO
                break;

            case Type.TASK:
                //@TODO
                break;

            case Type.TASK_ARRAY:
                List<String> taList = new ArrayList<>();
                while(buffer.read(cursor) != ARRS){
                    cursor++;
                }
                boolean isTaText = true;
                cursor++;
                startValue = ++cursor;
                while(buffer.read(cursor) != ARRE && isTaText){
                    if(buffer.read(cursor) == SEP){
                        taList.add(new String(buffer.slice(startValue,cursor-2)));
                        startValue = ++cursor;
                    }
                    if(buffer.read(cursor) == TEXT){
                        isText = !isTaText;
                    }
                    cursor++;
                }
                //Retrieve last element
                taList.add(new String(buffer.slice(startValue,cursor-2)));

                return new TypedObject(Type.TASK_ARRAY, taList);

            case Type.NODE:
                //@TODO
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

                return new TypedObject(Type.INT_TO_INT_MAP, iiMap);

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

                return new TypedObject(Type.INT_TO_STRING_MAP, isMap);

            case Type.INDEX:
                //@TODO
                break;

            case Type.KDTREE:
                //@TODO
                break;

            case Type.NDTREE:
                //@TODO
                break;
        }

        return null;
    }

    /**
     * Returns if a type is a primitive one or not
     * @param type Type to Test
     * @return True if the object is a Complex Type / False if primitive type
     */
    private boolean isComplexType(int type){
        return type != Type.BOOL && type != Type.INT && type != Type.DOUBLE && type != Type.LONG && type != Type.STRING;
    }

    private Object initObject(Object toSet, TypedObject base){
        switch(base.getType()){
            case Type.DOUBLE_ARRAY:
                DoubleArray dArray = (DoubleArray) toSet;
                List<Double> dList = (List<Double>) base.getObject();

                dArray.initWith(dList.stream().mapToDouble(d -> d).toArray());
                return dArray;

            case Type.LONG_ARRAY:
                LongArray lArray = (LongArray) toSet;
                List<Long> lList = (List<Long>) base.getObject();

                lArray.initWith(lList.stream().mapToLong(l -> l).toArray());
                return lArray;

            case Type.INT_ARRAY:
                IntArray iArray = (IntArray) toSet;
                List<Integer> iList = (List<Integer>) base.getObject();

                iArray.initWith(iList.stream().mapToInt(l -> l).toArray());
                return iArray;

            case Type.STRING_ARRAY:
                StringArray sArray = (StringArray) toSet;
                List<String> sList = (List<String>) base.getObject();

                sArray.initWith(sList.toArray(new String[sList.size()]));
                return sArray;

            case Type.LONG_TO_LONG_MAP:
                LongLongMap llMap = (LongLongMap) toSet;
                Map<Long, Long> llMapObject = (Map<Long,Long>) base.getObject();

                for(Long id : llMapObject.keySet()){
                    llMap.put(id, llMapObject.get(id));
                }
                return llMap;

            case Type.LONG_TO_LONG_ARRAY_MAP:
                LongLongArrayMap llaMap = (LongLongArrayMap) toSet;
                Map<Long, List<Long>> llaObject = (Map<Long, List<Long>>) base.getObject();

                for(Long id : llaObject.keySet()){
                    for(Long value: llaObject.get(id)){
                        llaMap.put(id,value);
                    }
                }
                return llaMap;

            case Type.STRING_TO_INT_MAP:
                StringIntMap siMap = (StringIntMap) toSet;
                Map<String, Integer> siMapObject = (Map<String, Integer>) base.getObject();

                for(String id : siMapObject.keySet()){
                    siMap.put(id, siMapObject.get(id));
                }
                return siMap;

            case Type.RELATION:
                //@TODO
                break;

            case Type.DMATRIX:
                DMatrix dMat = (DMatrix) toSet;
                List<Double> dMatList = (List<Double>) base.getObject();

                int xSize = dMatList.get(0).intValue();
                int ySize = dMatList.get(1).intValue();

                dMat.init(xSize, ySize);

                for(int i = 0 ; i < xSize; i++) {
                    for(int j= 0; j < ySize; j++){
                        dMat.set(i,j,dMatList.get(i+j+2));
                    }
                }
                return dMat;

            case Type.LMATRIX:
                LMatrix lMat = (LMatrix) toSet;
                List<Long> lMatList = (List<Long>) base.getObject();

                int xLSize = lMatList.get(0).intValue();
                int yLSize = lMatList.get(1).intValue();

                lMat.init(xLSize, yLSize);

                for(int i = 0 ; i < xLSize; i++) {
                    for(int j= 0; j < yLSize; j++){
                        lMat.set(i,j,lMatList.get(i+j+2));
                    }
                }
                return lMat;

            case Type.ESTRUCT:
                //@TODO
                break;

            case Type.ESTRUCT_ARRAY:
                //@TODO
                break;

            case Type.ERELATION:
                //@TODO
                break;

            case Type.TASK:
                //@TODO
                break;

            case Type.TASK_ARRAY:
                //@Todo Won't work since no getorcreate for this
                Task[] tArray = (Task[]) toSet;
                List<String> taList = (List<String>) base.getObject();

            case Type.NODE:
                //@TODO
                break;

            case Type.INT_TO_INT_MAP:
                IntIntMap iiMap = (IntIntMap) toSet;
                Map<Integer, Integer> iiMapObject = (Map<Integer, Integer>) base.getObject();

                for(Integer id : iiMapObject.keySet()){
                    iiMap.put(id,iiMapObject.get(id));
                }
                return iiMap;

            case Type.INT_TO_STRING_MAP:
                IntStringMap isMap = (IntStringMap) toSet;
                Map<Integer, String> isMapObject = (Map<Integer, String>) base.getObject();

                for(Integer id : isMapObject.keySet()){
                    isMap.put(id,isMapObject.get(id));
                }
                return isMap;

            case Type.INDEX:
                //@TODO
                break;

            case Type.KDTREE:
                //@TODO
                break;

            case Type.NDTREE:
                //@TODO
                break;
        }
        return null;
    }
}
