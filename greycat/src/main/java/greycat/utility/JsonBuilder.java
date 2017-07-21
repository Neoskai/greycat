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

package greycat.utility;

import greycat.*;
import greycat.internal.custom.KDTree;
import greycat.internal.custom.NDTree;
import greycat.plugin.NodeState;
import greycat.plugin.NodeStateCallback;
import greycat.struct.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * @ignore ts
 */
public class JsonBuilder {

    public static String buildJson(int type, Object elem) {
        final boolean[] isFirst = {true};
        final StringBuilder builder = new StringBuilder();

        builder.append("[");
        switch(type){
            case Type.BOOL:
                builder.append(Type.BOOL);
                builder.append(",");
                if ((Boolean) elem) {
                    builder.append("true");
                } else {
                    builder.append("false");
                }
                break;

            case Type.STRING:
                builder.append(Type.STRING);
                builder.append(",");
                builder.append("\"");
                builder.append((String) elem);
                builder.append("\"");
                break;

            case Type.LONG:
                builder.append(Type.LONG);
                builder.append(", ");
                builder.append((Long) elem);
                break;

            case Type.INT:
                builder.append(Type.INT);
                builder.append(",");
                builder.append((Integer) elem);
                break;

            case Type.DOUBLE:
                builder.append(Type.DOUBLE);
                builder.append(",");
                builder.append((Double) elem);
                break;


            case Type.DOUBLE_ARRAY:
                builder.append(Type.DOUBLE_ARRAY);
                builder.append(",");
                builder.append("[");
                DoubleArray castedArr = ((DoubleArray) elem);
                for (int j = 0; j < castedArr.size(); j++) {
                    if (j != 0) {
                        builder.append(",");
                    }
                    builder.append(castedArr.get(j));
                }
                builder.append("]");
                break;

            case Type.LONG_ARRAY:
                builder.append(Type.LONG_ARRAY);
                builder.append(",");
                builder.append("[");
                LongArray castedArr2 = (LongArray) elem;
                for (int j = 0; j < castedArr2.size(); j++) {
                    if (j != 0) {
                        builder.append(",");
                    }
                    builder.append(castedArr2.get(j));
                }
                builder.append("]");
                break;

            case Type.INT_ARRAY:
                builder.append(Type.INT_ARRAY);
                builder.append(",");
                builder.append("[");
                IntArray castedArr3 = (IntArray) elem;
                for (int j = 0; j < castedArr3.size(); j++) {
                    if (j != 0) {
                        builder.append(",");
                    }
                    builder.append(castedArr3.get(j));
                }
                builder.append("]");
                break;

            case Type.STRING_ARRAY:
                builder.append(Type.STRING_ARRAY);
                builder.append(",");
                builder.append("[");
                StringArray castedStrArr = (StringArray) elem;
                for (int j = 0; j < castedStrArr.size(); j++) {
                    if (j != 0) {
                        builder.append(",");
                    }
                    builder.append("\"");
                    builder.append(castedStrArr.get(j));
                    builder.append("\"");
                }
                builder.append("]");
                break;

            case Type.LONG_TO_LONG_MAP:
                builder.append(Type.LONG_TO_LONG_MAP);
                builder.append(",");
                builder.append("{");
                LongLongMap castedMapL2L = (LongLongMap) elem;
                isFirst[0] = true;
                castedMapL2L.each(new LongLongMapCallBack() {
                    @Override
                    public void on(long key, long value) {
                        if (!isFirst[0]) {
                            builder.append(",");
                        } else {
                            isFirst[0] = false;
                        }
                        builder.append("\"");
                        builder.append(key);
                        builder.append("\":");
                        builder.append(value);
                    }
                });
                builder.append("}");
                break;

            // @TODO A VERIFIER
            case Type.LONG_TO_LONG_ARRAY_MAP:
                builder.append(Type.LONG_TO_LONG_ARRAY_MAP);
                builder.append(",");
                builder.append("{");
                LongLongArrayMap castedMapL2LA = (LongLongArrayMap) elem;
                isFirst[0] = true;

                Set<Long> keys = new HashSet<Long>();
                castedMapL2LA.each(new LongLongArrayMapCallBack() {
                    @Override
                    public void on(long key, long value) {
                        keys.add(key);
                    }
                });
                final Long[] flatKeys = keys.toArray(new Long[keys.size()]);
                for (int i = 0; i < flatKeys.length; i++) {
                    long[] values = castedMapL2LA.get(flatKeys[i]);
                    if (!isFirst[0]) {
                        builder.append(",");
                    } else {
                        isFirst[0] = false;
                    }
                    builder.append("\"");
                    builder.append(flatKeys[i]);
                    builder.append("\":[");
                    for (int j = 0; j < values.length; j++) {
                        if (j != 0) {
                            builder.append(",");
                        }
                        builder.append(values[j]);
                    }
                    builder.append("]");
                }
                builder.append("}");
                break;

            case Type.STRING_TO_INT_MAP:
                builder.append(Type.STRING_TO_INT_MAP);
                builder.append(",");
                builder.append("{");
                StringIntMap castedMapS2L = (StringIntMap) elem;
                isFirst[0] = true;
                castedMapS2L.each(new StringLongMapCallBack() {
                    @Override
                    public void on(String key, long value) {
                        if (!isFirst[0]) {
                            builder.append(",");
                        } else {
                            isFirst[0] = false;
                        }
                        builder.append("\"");
                        builder.append(key);
                        builder.append("\":");
                        builder.append(value);
                    }
                });
                builder.append("}");
                break;


            case Type.RELATION:
                builder.append(Type.RELATION);
                builder.append(",");
                builder.append("[");
                Relation castedRelArr = (Relation) elem;
                for (int j = 0; j < castedRelArr.size(); j++) {
                    if (j != 0) {
                        builder.append(",");
                    }
                    builder.append(castedRelArr.get(j));
                }
                builder.append("]");
                break;


            case Type.DMATRIX:
                builder.append(Type.DMATRIX);
                builder.append(",");
                builder.append("[");
                DMatrix castedDMat = (DMatrix) elem;

                builder.append(castedDMat.rows());
                builder.append(",");

                builder.append(castedDMat.columns());
                builder.append(",");

                for(int i = 0 ; i < castedDMat.rows(); i++) {
                    for(int j= 0; j < castedDMat.columns(); j++){
                        if(j != 0 || i != 0){
                            builder.append(",");
                        }
                        builder.append(castedDMat.get(i,j));
                    }
                }
                builder.append("]");
                break;

            case Type.LMATRIX:
                builder.append(Type.LMATRIX);
                builder.append(",");
                builder.append("[");

                LMatrix castedLMat = (LMatrix) elem;

                builder.append(castedLMat.rows());
                builder.append(",");

                builder.append(castedLMat.columns());
                builder.append(",");

                for(int i = 0 ; i < castedLMat.rows(); i++) {
                    for(int j= 0; j < castedLMat.columns(); j++){
                        if(j != 0 || i != 0){
                            builder.append(",");
                        }
                        builder.append(castedLMat.get(i,j));
                    }
                }

                builder.append("]");
                break;


            case Type.ESTRUCT:
                builder.append(Type.ESTRUCT);
                builder.append(",");

                EStruct castedEStruct = (EStruct) elem;
                builder.append(castedEStruct.toJson());
                break;

            case Type.ESTRUCT_ARRAY:
                builder.append(Type.ESTRUCT_ARRAY);
                builder.append(",");

                EStructArray castedEArr = (EStructArray) elem;
                builder.append(castedEArr.toJson());
                break;

            case Type.ERELATION:
                builder.append(Type.ERELATION);
                builder.append(", ");

                ERelation castedErel = (ERelation) elem;
                // @Todo Switch from toString to toJson (Value is OK)
                builder.append(castedErel.toString());
                break;


            case Type.TASK:
                builder.append(Type.TASK);
                builder.append(",");
                builder.append("\"");

                Task castedTask = (Task) elem;
                // @Todo Switch from toString to toJson (value OK)
                builder.append(castedTask.toString());
                builder.append("\"");
                break;

            case Type.TASK_ARRAY:
                builder.append(Type.TASK_ARRAY);
                builder.append(",");
                builder.append("[");

                Task[] castedTaskArr = (Task[]) elem;
                for (int i = 0; i < castedTaskArr.length; i++){
                    if(i != 0){
                        builder.append(",");
                    }
                    builder.append("\"");
                    // @Todo Switch from toString to toJson
                    builder.append(castedTaskArr[i].toString());
                    builder.append("\"");
                }

                builder.append("]");
                break;

            case Type.NODE:
                builder.append(Type.NODE);
                builder.append(",");

                Node castedNode = (Node) elem;
                final NodeState state = castedNode.graph().resolver().resolveState(castedNode);
                isFirst[0] = true;

                builder.append("{");
                if (state != null) {
                    state.each(new NodeStateCallback() {
                        @Override
                        public void on(int attributeKey, int elemType, Object elem) {
                            if (elem != null) {
                                if(isFirst[0]){
                                    isFirst[0] = false;
                                } else {
                                    builder.append(",");
                                }

                                String resolveName = castedNode.graph().resolver().hashToString(attributeKey);
                                if (resolveName == null) {
                                    resolveName = attributeKey + "";
                                }

                                builder.append("\"");
                                builder.append(resolveName);
                                builder.append("\":");
                                builder.append(JsonBuilder.buildJson(elemType,elem));

                            }
                        }
                    });
                }
                builder.append("}");

                break;


            case Type.INT_TO_INT_MAP:
                builder.append(Type.INT_TO_INT_MAP);
                builder.append(",");
                builder.append("{");
                IntIntMap castedMapI2I = (IntIntMap) elem;
                isFirst[0] = true;
                castedMapI2I.each(new IntIntMapCallBack() {
                    @Override
                    public void on(int key, int value) {
                        if (!isFirst[0]) {
                            builder.append(",");
                        } else {
                            isFirst[0] = false;
                        }
                        builder.append("\"");
                        builder.append(key);
                        builder.append("\":");
                        builder.append(value);
                    }
                });
                builder.append("}");
                break;

            case Type.INT_TO_STRING_MAP:
                builder.append(Type.INT_TO_STRING_MAP);
                builder.append(",");
                builder.append("{");
                IntStringMap castedMapI2S = (IntStringMap) elem;
                isFirst[0] = true;
                castedMapI2S.each(new IntStringMapCallBack() {
                    @Override
                    public void on(int key, String value) {
                        if (!isFirst[0]) {
                            builder.append(",");
                        } else {
                            isFirst[0] = false;
                        }
                        builder.append("\"");
                        builder.append(key);
                        builder.append("\":");
                        builder.append("\"");
                        builder.append(value);
                        builder.append("\"");
                    }
                });
                builder.append("}");
                break;

            case Type.INDEX:
                try {
                    EStructArray castedIndex = (EStructArray) elem;

                    builder.append(Type.INDEX);
                    builder.append(",");
                    builder.append(buildJson(Type.ESTRUCT_ARRAY, castedIndex));
                } catch(ClassCastException e){
                    Node castedNodeIndex = (Node) elem;

                    builder.append(Type.INDEX);
                    builder.append(",");
                    builder.append(buildJson(Type.NODE, castedNodeIndex));
                }
                break;

            case Type.KDTREE:
                KDTree castedKTree = (KDTree) elem;

                builder.append(Type.KDTREE);
                builder.append(",{");
                builder.append(buildJson(Type.ESTRUCT_ARRAY, castedKTree.backend()));
                builder.append("}");
                break;

            case Type.NDTREE:
                NDTree castedNTree = (NDTree) elem;

                builder.append(Type.NDTREE);
                builder.append(",{");
                builder.append(buildJson(Type.ESTRUCT_ARRAY, castedNTree.backend()));
                builder.append("}");
                break;
        }
        builder.append("]");
        return builder.toString();
    }

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
                    JsonBuilder.buildObject(nodeJson.getJSONObject(i).toString(), castedNode);
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
