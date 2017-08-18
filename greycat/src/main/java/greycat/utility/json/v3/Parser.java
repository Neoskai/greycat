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
import greycat.struct.*;
import greycat.utility.json.v2.TypedObject;

import java.util.*;
import java.util.Map;

public class Parser {

    private final byte RECORD_BUILD = 1;
    private final byte NODE_BUILD = 2;
    private final byte VALUE_BUILD = 3;
    private final byte INDEX_BUILD = 4;

    private final byte TIMELIST_BUILD = 5;
    private final byte UNTYPED_BUILD = 6;
    private final byte DIRECT_SET = 7;
    private final byte CORENODE_BUILD = 8;

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
        List<Long> timeList = new ArrayList<>();

        List<Object> arrayElements = new ArrayList<>();

        int objStack = 0;
        int arrStack = 0;
        int currentTime = -1;

        int objType = -1;

        for(int i = 0; i < index.size; i++){
            int type = index.type[i];
            int length = index.length[i];
            int start = index.start[i];

            System.out.println(new String(buffer.slice(start,start+length-1)));

            switch(type){
                case JsonType.JSON_OBJECT_START:
                    objStack++;
                    switch(objStack){
                        case 1: // [ {
                            state = RECORD_BUILD;
                            break;
                        case 2: // [ { values:[ [ 21, {
                            state = NODE_BUILD;
                            break;
                    }
                    break;

                case JsonType.JSON_ARRAY_START:
                    arrStack++;
                    if(arrStack == 3){ // [ { values: [ [ --> 21, {...
                        currentTime++;
                    }
                    if(arrStack == 3 ){
                        state = CORENODE_BUILD;
                    } else if (arrStack > 2 && !isFinal(parents.peek().getType())){
                        state = UNTYPED_BUILD;
                    }
                    break;

                case JsonType.JSON_OBJECT_END:
                    objStack--;
                    // @TODO CHeck ARRAY END OF LEVEL 2
                    if(objStack == 2 && isObjectEnding(parents.peek().getType())){
                        TypedObject directParent = parents.pop();
                        System.out.println("----> Poping from stack: " + directParent.getType());
                        switch(directParent.getType()) { // We already pushed the empty object to the stack, so we pop it
                            case Type.LONG_TO_LONG_MAP:
                                LongLongMap castedLLMap = (LongLongMap) directParent.getObject();
                                for(int z=0; z< (arrayElements.size()-1); z+=2){
                                    castedLLMap.put(Long.parseLong((String) arrayElements.get(z)), Long.parseLong((String) arrayElements.get(z+1)));
                                }
                                break;

                            case Type.LONG_TO_LONG_ARRAY_MAP:
                                //@TODO Check how to precast in array
                                break;

                            case Type.STRING_TO_INT_MAP:
                                StringIntMap castedSIMap = (StringIntMap) directParent.getObject();
                                for(int z=0; z< (arrayElements.size()-1); z+=2){
                                    castedSIMap.put((String) arrayElements.get(z),Integer.parseInt((String) arrayElements.get(z+1)));
                                }
                                break;
                            case Type.INT_TO_INT_MAP:
                                IntIntMap castedIIMap = (IntIntMap) directParent.getObject();
                                for(int z=0; z< (arrayElements.size()-1); z+=2){
                                    castedIIMap.put(Integer.parseInt((String) arrayElements.get(z)),Integer.parseInt((String) arrayElements.get(z+1)));
                                }
                                break;

                            case Type.INT_TO_STRING_MAP:
                                IntStringMap castedISMap = (IntStringMap) directParent.getObject();
                                for(int z=0; z< (arrayElements.size()-1); z+=2){
                                    castedISMap.put(Integer.parseInt((String) arrayElements.get(z)),(String) arrayElements.get(z+1));
                                }
                                break;
                        }

                        // [ { values:[[21,{}  --> ]
                        // End of value, need to set the object with travel in time
                        /*if (parents.peek().getType() == Type.NODE) {
                            Node n = (Node) parents.peek().getObject();
                            n.travelInTime(timeList.get(currentTime), new Callback<Node>() {
                                @Override
                                public void on(Node result) {
                                    // Create child and set value
                                }
                            });
                        }*/
                    }
                    break;

                case JsonType.JSON_ARRAY_END:
                    arrStack--;
                    if (state == TIMELIST_BUILD){
                        state = RECORD_BUILD;
                        // We have enough information to build the node, so we build it and put it in the stack
                        _graph.connect(null);
                        _graph.lookup((long) properties.get("world"), timeList.get(0), (long) properties.get("id"), new Callback<Node>() {
                            @Override
                            public void on(Node result) {
                                System.out.println("-----> Adding First Node");
                                if (result == null){
                                    // @TODO : Use nodetype to create an instance of the good type
                                    Node newNode = new BaseNode((long) properties.get("world"), (long) timeList.get(0), (long) properties.get("id"), _graph);
                                    _graph.resolver().initNode(newNode, Constants.NULL_LONG);

                                    parents.push(new TypedObject(Type.NODE, newNode));

                                } else {
                                    parents.push(new TypedObject(Type.NODE, result));
                                }
                            }
                        });
                        _graph.disconnect(null);
                    }
                    if( arrStack == 2){ // Last level of parents stack
                        // Build last level
                        System.out.println(" ------> Pop Last Node");
                        parents.pop();
                        parents.clear();
                    }
                    if(state == VALUE_BUILD && arrStack > 3 && !isBasic(parents.peek().getType())){// We ended to retrieve properties from an array Object / we now need to set the value to the parent
                        TypedObject directParent = parents.pop();
                        System.out.println("----> Poping from stack: " + directParent.getType());
                        switch(directParent.getType()){ // We already pushed the empty object to the stack, so we pop it
                            //We then fill it with the values, and set it as value to the parent.
                            case Type.DOUBLE_ARRAY:
                                DoubleArray castedArray = (DoubleArray) directParent.getObject();
                                castedArray.init(arrayElements.size());
                                for(int z = 0; z< arrayElements.size(); z++){
                                    castedArray.set(z, Double.parseDouble((String) arrayElements.get(z)));
                                }
                                break;

                            case Type.LONG_ARRAY:
                                LongArray castedLArray = (LongArray) directParent.getObject();
                                castedLArray.init(arrayElements.size());
                                for(int z = 0; z< arrayElements.size(); z++){
                                    castedLArray.set(z, Long.parseLong( (String) arrayElements.get(z)));
                                }
                                break;

                            case Type.INT_ARRAY:
                                IntArray castedIArray = (IntArray) directParent.getObject();
                                castedIArray.init(arrayElements.size());
                                for(int z = 0; z< arrayElements.size(); z++){
                                    castedIArray.set(z, Integer.parseInt( (String) arrayElements.get(z)));
                                }

                                break;

                            case Type.STRING_ARRAY:
                                StringArray castedSArray = (StringArray) directParent.getObject();
                                castedSArray.init(arrayElements.size());
                                for(int z = 0; z< arrayElements.size(); z++){
                                    castedSArray.set(z, (String) arrayElements.get(z));
                                }

                                break;

                            case Type.RELATION:
                                // @TODO check how to recreate
                                break;

                            case Type.DMATRIX:
                                DMatrix castedDMat = (DMatrix) directParent.getObject();

                                int xSize = Integer.parseInt((String) arrayElements.get(0));
                                int ySize = Integer.parseInt((String) arrayElements.get(1));

                                castedDMat.init(xSize, ySize);

                                for(int y = 0 ; y < xSize; y++) {
                                    for(int z= 0; z < ySize; z++){
                                        castedDMat.set(y,z,Double.parseDouble((String) arrayElements.get(y+z+2)));
                                    }
                                }
                                break;

                            case Type.LMATRIX:
                                LMatrix castedLMat = (LMatrix) directParent.getObject();

                                int xLSize = Integer.parseInt((String) arrayElements.get(0));
                                int yLSize = Integer.parseInt((String) arrayElements.get(1));

                                castedLMat.init(xLSize, yLSize);

                                for(int y = 0 ; y < xLSize; y++) {
                                    for(int z= 0; z < yLSize; z++){
                                        castedLMat.set(y,z,Long.parseLong((String) arrayElements.get(y+z+2)));
                                    }
                                }
                                break;

                            case Type.ESTRUCT:
                                //@TODO
                                break;

                            case Type.ESTRUCT_ARRAY:
                                // @TODO
                                break;

                            case Type.ERELATION:
                                //@TODO
                                break;

                            case Type.TASK:
                                //@TODO
                                break;

                            case Type.TASK_ARRAY:
                                break;

                            case Type.NODE:
                                //@TODO
                                break;

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
                        arrayElements.clear();
                    }
                    if(arrStack == 2){
                        properties.clear();
                    }
                    break;

                case JsonType.JSON_PROPERTY_NAME:
                    currentProperty = new String(buffer.slice(start,start+length-1));

                    if("times".equals(currentProperty) || "time".equals(currentProperty))
                        state = TIMELIST_BUILD;
                    if("values".equals(currentProperty) || "value".equals(currentProperty))
                        state = NODE_BUILD;
                    break;

                case JsonType.JSON_ARRAY_VALUE_NUMBER:
                    System.out.println("Array number in state: " + state);
                    if(state == TIMELIST_BUILD)
                        timeList.add(Long.parseLong(new String(buffer.slice(start,start+length-1))));

                    if(state == CORENODE_BUILD){
                        _graph.connect(null);
                        parents.add(new TypedObject(Type.NODE, _graph.newNode(0,timeList.get(0))));
                        _graph.disconnect(null);
                    }

                    if(state == UNTYPED_BUILD) {
                        objType = Integer.parseInt(new String(buffer.slice(start, start + length - 1)));
                        state = VALUE_BUILD;

                        // At this point we should have both the name of the value and it's type, so init the object and set it as parent if needed
                        Object parent = parents.peek().getObject();
                        int parentType = parents.peek().getType();

                        switch (parentType){
                            case Type.NODE:
                                Node castedNParent = (Node) parent;
                                Object newValue = castedNParent.getOrCreate(currentProperty, objType);
                                // Handle types with no getOrCreate
                                // Meaning : Bool / Int / Double / Long / String / Relation / ERelation / Task / TaskArray / Node / EStruct / KDTREE / NDTREE / Index
                                // Bool / Int / Double / Long / String are directly assigned when read
                                if(newValue == null){
                                    System.out.println("Created null object");
                                }
                                System.out.println("----> Adding to Stack: " + objType);
                                parents.push(new TypedObject(objType, newValue));
                                break;
                            // All other cases to handle

                            // if basic type, directly set value
                        }

                        if (isBasic(parentType)){
                            state = DIRECT_SET;
                        }
                    }
                    else{
                        arrayElements.add(new String(buffer.slice(start, start+ length -1)));
                    }
                    // If we are not building a type, we are retrieving the elements from a real array

                    break;
                case JsonType.JSON_ARRAY_VALUE_STRING:
                    arrayElements.add(new String(buffer.slice(start, start+ length -1)));
                    break;
                case JsonType.JSON_ARRAY_VALUE_STRING_ENC:
                    arrayElements.add(new String(buffer.slice(start, start+ length -1)));
                    break;
                case JsonType.JSON_ARRAY_VALUE_BOOLEAN:
                    arrayElements.add(new String(buffer.slice(start, start+ length -1)));
                    break;

                case JsonType.JSON_PROPERTY_VALUE_STRING:
                    if (state == RECORD_BUILD)
                        properties.put(currentProperty, new String(buffer.slice(start, start+length-1)));

                    if (state == DIRECT_SET){
                        switch (parents.peek().getType()){
                            case Type.NODE:
                                Node parsedNode = (Node) parents.peek().getObject();
                                parsedNode.set(currentProperty, Type.STRING, new String(buffer.slice(start,start+length-1)));
                                break;
                            case Type.ESTRUCT:
                                EStruct parsedEStruct = (EStruct) parents.peek().getObject();
                                parsedEStruct.set(currentProperty, Type.STRING, new String(buffer.slice(start, start+length-1)));
                                break;
                        }
                    }

                    break;

                case JsonType.JSON_PROPERTY_VALUE_STRING_ENC:
                    if(state == RECORD_BUILD)
                        properties.put(currentProperty, new String(buffer.slice(start, start+length-1)));

                    if (state == DIRECT_SET){
                        switch (parents.peek().getType()){
                            case Type.NODE:
                                Node parsedNode = (Node) parents.peek().getObject();
                                parsedNode.set(currentProperty, Type.STRING, new String(buffer.slice(start,start+length-1)));
                                break;
                            case Type.ESTRUCT:
                                EStruct parsedEStruct = (EStruct) parents.peek().getObject();
                                parsedEStruct.set(currentProperty, Type.STRING, new String(buffer.slice(start, start+length-1)));
                                break;
                        }
                    }

                    break;
                case JsonType.JSON_PROPERTY_VALUE_NUMBER:
                    if(state == RECORD_BUILD)
                        properties.put(currentProperty, Long.parseLong(new String(buffer.slice(start, start+length-1))));

                    // Separate INT from Double from LONG

                    break;
                case JsonType.JSON_PROPERTY_VALUE_BOOLEAN:
                    if(state == RECORD_BUILD)
                        properties.put(currentProperty, Boolean.valueOf(new String(buffer.slice(start, start+length-1))));

                    if (state == DIRECT_SET){
                        switch (parents.peek().getType()){
                            case Type.NODE:
                                Node parsedNode = (Node) parents.peek().getObject();
                                parsedNode.set(currentProperty, Type.BOOL, Boolean.valueOf(new String(buffer.slice(start,start+length-1))));
                                break;
                            case Type.ESTRUCT:
                                EStruct parsedEStruct = (EStruct) parents.peek().getObject();
                                parsedEStruct.set(currentProperty, Type.BOOL, Boolean.valueOf(new String(buffer.slice(start, start+length-1))));
                                break;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Tells if a parent can still have children or not
     * @param type The type to check for
     * @return True if can't have children / False if still can have children
     */
    boolean isFinal(int type){
        return type != Type.ESTRUCT_ARRAY && type != Type.ESTRUCT && type != Type.NODE && type != Type.ERELATION
                && type != Type.INDEX && type != Type.KDTREE && type != Type.NDTREE && !Type.isCustom(type);
    }

    /**
     * Returns if we have a basic type or not
     * @param type The type to test
     * @return True if is basic / False if not
     */
    boolean isBasic(int type){
        return type == Type.BOOL || type == Type.INT || type == Type.STRING || type == Type.DOUBLE || type == Type.LONG;
    }

    boolean isObjectEnding(int type){
        return type == Type.STRING_TO_INT_MAP || type == Type.INT_TO_STRING_MAP || type == Type.LONG_TO_LONG_MAP || type == Type.LONG_TO_LONG_ARRAY_MAP
                || type == Type.INT_TO_INT_MAP;
    }
}
