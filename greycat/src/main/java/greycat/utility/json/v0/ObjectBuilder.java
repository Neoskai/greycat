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

package greycat.utility.json.v0;

import greycat.Container;
import greycat.Node;
import greycat.Type;
import greycat.internal.custom.KDTree;
import greycat.internal.custom.NDTree;
import greycat.struct.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @ignore ts
 */
public class ObjectBuilder {
    public static Container buildObject(String json, Container parent) {
        JSONObject jsonObject = new JSONObject(json);
        String name = jsonObject.getString("_name");

        JSONArray baseArray = jsonObject.getJSONArray("_value");

        int type = baseArray.getInt(0);

        Object obj = null;

        switch (type) {
            case Type.BOOL:
                obj = baseArray.getBoolean(1);
                break;

            case Type.STRING:
                obj = baseArray.getString(1);
                break;

            case Type.LONG:
                obj = baseArray.getLong(1);
                break;

            case Type.INT:
                obj = baseArray.getInt(1);
                break;

            case Type.DOUBLE:
                obj = baseArray.getDouble(1);
                break;


            case Type.DOUBLE_ARRAY:
                obj = parent.getOrCreate(name, type);
                DoubleArray castedArray = (DoubleArray) obj;

                JSONArray jDArray = baseArray.getJSONArray(1);
                castedArray.init(jDArray.length());

                for(int i=0; i < jDArray.length(); i++){
                    castedArray.set(i,jDArray.getDouble(i));
                }

                break;

            case Type.LONG_ARRAY:
                obj = parent.getOrCreate(name, type);
                JSONArray jLArray = jsonObject.getJSONArray("_value");

                LongArray castedLArray = (LongArray) obj;
                castedLArray.init(jLArray.length());

                for(int i=0; i < jLArray.length(); i++){
                    castedLArray.set(i,jLArray.getLong(i));
                }

                break;

            case Type.INT_ARRAY:
                obj = parent.getOrCreate(name, type);
                JSONArray jIArray = jsonObject.getJSONArray("_value");

                IntArray castedIArray = (IntArray) obj;
                castedIArray.init(jIArray.length());

                for(int i=0; i < jIArray.length(); i++){
                    castedIArray.set(i,jIArray.getInt(i));
                }

                break;

            case Type.STRING_ARRAY:
                obj = parent.getOrCreate(name, type);
                JSONArray jSArray = jsonObject.getJSONArray("_value");

                StringArray castedSArray = (StringArray) obj;
                castedSArray.init(jSArray.length());

                for(int i=0; i < jSArray.length(); i++){
                    castedSArray.set(i, jSArray.getString(i));
                }

                break;

            case Type.LONG_TO_LONG_MAP:
                obj = parent.getOrCreate(name, type);
                LongLongMap castedLLmap = (LongLongMap) obj;

                JSONObject llObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysItr = llObject.keys();
                while(keysItr.hasNext()){
                    String key = keysItr.next();

                    Long keyLong = Long.parseLong(key);
                    Long value = llObject.getLong(key);
                    castedLLmap.put(keyLong,value);
                }
                break;

            // @TODO A Verifier
            case Type.LONG_TO_LONG_ARRAY_MAP:
                obj = parent.getOrCreate(name, type);

                LongLongArrayMap castedLLAMap = (LongLongArrayMap) obj;
                JSONObject llaObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysllaItr = llaObject.keys();
                while(keysllaItr.hasNext()){
                    String key = keysllaItr.next();
                    Long keyLong = Long.parseLong(key);

                    JSONArray array = llaObject.getJSONArray(key);

                    for(int i = 0; i < array.length(); i++){
                        castedLLAMap.put(keyLong, array.getLong(i));
                    }

                }
                break;

            case Type.STRING_TO_INT_MAP:
                obj = parent.getOrCreate(name, type);
                StringIntMap castedSIMap = (StringIntMap) obj;

                JSONObject siObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysSiItr = siObject.keys();
                while(keysSiItr.hasNext()){
                    String key = keysSiItr.next();
                    Integer value = siObject.getInt(key);

                    castedSIMap.put(key,value);
                }
                break;


            // @Todo
            case Type.RELATION:
                JSONArray rjArray = jsonObject.getJSONArray("_value");

                Long[] rarray = new Long[rjArray.length()];
                for(int i=0; i < rjArray.length(); i++){
                    rarray[i] = rjArray.getLong(i);
                }

                break;


            case Type.DMATRIX:
                obj = parent.getOrCreate(name, type);
                JSONArray dmjArray = jsonObject.getJSONArray("_value");

                DMatrix castedDmat = (DMatrix) obj;

                int xSize = dmjArray.getInt(0);
                int ySize = dmjArray.getInt(1);

                castedDmat.init(xSize, ySize);

                for(int i=0; i < dmjArray.length()-2; i++){
                    castedDmat.add(Math.floorDiv(i,xSize),i%ySize, dmjArray.getDouble(i+2));
                }
                break;

            case Type.LMATRIX:
                obj = parent.getOrCreate(name, type);
                JSONArray lmjArray = jsonObject.getJSONArray("_value");

                LMatrix castedLmat = (LMatrix) obj;

                int xLSize = lmjArray.getInt(0);
                int yLSize = lmjArray.getInt(1);

                castedLmat.init(xLSize,yLSize);

                for(int i=0; i < lmjArray.length(); i++){
                    castedLmat.add(Math.floorDiv(i,xLSize),i%yLSize, lmjArray.getLong(i+2));
                }
                break;


            case Type.ESTRUCT:
                obj = parent.getOrCreate(name,type);

                JSONArray esJson = jsonObject.getJSONArray("_value");
                EStruct castedEstr = (EStruct) obj;

                castedEstr.fromJson(esJson.toString());
                break;

            case Type.ESTRUCT_ARRAY:
                obj = parent.getOrCreate(name,type);

                JSONArray esaJson = jsonObject.getJSONArray("_value");

                EStructArray castedEarr = (EStructArray) obj;

                castedEarr.fromJson(esaJson.toString());

                break;

            //@Todo
            case Type.ERELATION:
                break;

            //@Todo
            case Type.TASK:
                break;

            //@Todo
            case Type.TASK_ARRAY:
                break;

            case Type.NODE:
                obj = parent.getOrCreate(name, type);

                JSONArray nodeJson = jsonObject.getJSONArray("_value");

                Node castedNode = (Node) obj;

                for(int i = 0; i < nodeJson.length(); i++){
                    buildObject(nodeJson.getJSONObject(i).toString(), castedNode);
                }

                break;


            case Type.INT_TO_INT_MAP:
                obj = parent.getOrCreate(name, type);
                IntIntMap castedIIMap = (IntIntMap) obj;

                JSONObject iiObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysIiItr = iiObject.keys();
                while(keysIiItr.hasNext()){
                    String key = keysIiItr.next();

                    Integer iKey = Integer.parseInt(key);
                    Integer value = iiObject.getInt(key);

                    castedIIMap.put(iKey,value);
                }
                break;

            case Type.INT_TO_STRING_MAP:
                obj = parent.getOrCreate(name, type);
                IntStringMap castedISMap = (IntStringMap) obj;

                JSONObject isObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysIsItr = isObject.keys();
                while(keysIsItr.hasNext()){
                    String key = keysIsItr.next();

                    Integer iKey = Integer.parseInt(key);
                    String value = isObject.getString(key);

                    castedISMap.put(iKey,value);
                }
                break;

            //@Todo
            case Type.INDEX:
                break;

            case Type.KDTREE:
                obj = parent.getOrCreate(name, type);

                JSONObject kdJson = jsonObject.getJSONObject("_value");

                KDTree castedKD = (KDTree) obj;
                castedKD.backend().fromJson(kdJson.toString());

                break;

            case Type.NDTREE:
                obj = parent.getOrCreate(name, type);

                JSONObject ndJson = jsonObject.getJSONObject("_value");

                NDTree castedND = (NDTree) obj;

                castedND.backend().fromJson(ndJson.toString());

                break;
        }

        parent.set(name, type, obj);

        return parent;
    }
}
