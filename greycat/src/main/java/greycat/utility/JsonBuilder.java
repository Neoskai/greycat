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

import greycat.Node;
import greycat.Task;
import greycat.Type;
import greycat.internal.heap.HeapContainer;
import greycat.plugin.NodeState;
import greycat.plugin.NodeStateCallback;
import greycat.struct.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.Map;

/**
 * @ignore ts
 */
public class JsonBuilder {

    public static String buildJson(int type, Object elem) {
        final boolean[] isFirst = {true};
        final StringBuilder builder = new StringBuilder();
        builder.append("{");
        switch(type){
            case Type.BOOL:
                builder.append("\"_type\":");
                builder.append(Type.BOOL);
                builder.append(", \"_value\":");
                if ((Boolean) elem) {
                    builder.append("true");
                } else {
                    builder.append("false");
                }
                break;

            case Type.STRING:
                builder.append("\"_type\":");
                builder.append(Type.STRING);
                builder.append(", \"_value\":");
                builder.append("\"");
                builder.append((String) elem);
                builder.append("\"");
                break;

            case Type.LONG:
                builder.append("\"_type\":");
                builder.append(Type.LONG);
                builder.append(", \"_value\":");
                builder.append((Long) elem);
                break;

            case Type.INT:
                builder.append("\"_type\":");
                builder.append(Type.INT);
                builder.append(", \"_value\":");
                builder.append((Integer) elem);
                break;

            case Type.DOUBLE:
                builder.append("\"_type\":");
                builder.append(Type.DOUBLE);
                builder.append(", \"_value\":");
                builder.append((Double) elem);
                break;


            case Type.DOUBLE_ARRAY:
                builder.append("\"_type\":");
                builder.append(Type.DOUBLE_ARRAY);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.LONG_ARRAY);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.INT_ARRAY);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.STRING_ARRAY);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.LONG_TO_LONG_MAP);
                builder.append(", \"_value\":");
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

            case Type.LONG_TO_LONG_ARRAY_MAP:
                builder.append("\"_type\":");
                builder.append(Type.LONG_TO_LONG_ARRAY_MAP);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.STRING_TO_INT_MAP);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.RELATION);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.DMATRIX);
                builder.append(", \"_value\":");
                builder.append("[");

                DMatrix castedDMat = (DMatrix) elem;
                for(int i = 0 ; i < castedDMat.rows(); i++) {
                    if(i != 0){
                        builder.append(",");
                    }
                    builder.append("[");
                    for(int j= 0; j < castedDMat.columns(); j++){
                        if(j != 0){
                            builder.append(",");
                        }
                        builder.append(castedDMat.get(i,j));
                    }
                    builder.append("]");
                }
                builder.append("]");
                break;

            case Type.LMATRIX:
                builder.append("\"_type\":");
                builder.append(Type.LMATRIX);
                builder.append(", \"_value\":");
                builder.append("[");

                DMatrix castedLMat = (DMatrix) elem;
                for(int i = 0 ; i < castedLMat.rows(); i++) {
                    if(i != 0){
                        builder.append(",");
                    }
                    builder.append("[");
                    for(int j= 0; j < castedLMat.columns(); j++){
                        if(j != 0){
                            builder.append(",");
                        }
                        builder.append(castedLMat.get(i,j));
                    }
                    builder.append("]");
                }
                builder.append("]");
                break;


            case Type.ESTRUCT:
                builder.append("\"_type\":");
                builder.append(Type.ESTRUCT);
                builder.append(", \"_value\":");

                EStruct castedEStruct = (EStruct) elem;
                builder.append(castedEStruct.toJson());
                break;

            case Type.ESTRUCT_ARRAY:
                builder.append("\"_type\":");
                builder.append(Type.ESTRUCT_ARRAY);
                builder.append(", \"_value\":");

                EStructArray castedEArr = (EStructArray) elem;
                // @Todo Switch from toString to toJson
                builder.append(castedEArr.toString());
                break;

            case Type.ERELATION:
                builder.append("\"_type\":");
                builder.append(Type.ERELATION);
                builder.append(", \"_value\":");

                ERelation castedErel = (ERelation) elem;
                // @Todo Switch from toString to toJson
                builder.append(castedErel.toString());
                break;


            case Type.TASK:
                builder.append("\"_type\":");
                builder.append(Type.TASK);
                builder.append(", \"_value\":");
                builder.append("\"");

                Task castedTask = (Task) elem;
                // @Todo Switch from toString to toJson
                builder.append(castedTask.toString());
                builder.append("\"");
                break;

            case Type.TASK_ARRAY:
                builder.append("\"_type\":");
                builder.append(Type.TASK_ARRAY);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.NODE);
                builder.append(", \"_value\":");
                builder.append("{");

                Node castedNode = (Node) elem;

                builder.append("\"_world\":");
                builder.append(castedNode.world());
                builder.append(",\"_time\":");
                builder.append(castedNode.time());
                builder.append(",\"_id\":");
                builder.append(castedNode.id());
                builder.append(",\"_nodetype\":");
                builder.append(castedNode.nodeTypeName());

                builder.append(",\"_elems\":");
                builder.append("[");

                isFirst[0] = true;
                final NodeState state = castedNode.graph().resolver().resolveState(castedNode);
                if (state != null) {
                    state.each(new NodeStateCallback() {
                        @Override
                        public void on(int attributeKey, int elemType, Object elem) {
                            if (elem != null) {
                                String resolveName = castedNode.graph().resolver().hashToString(attributeKey);
                                if (resolveName == null) {
                                    resolveName = attributeKey + "";
                                }

                                if (!isFirst[0]) {
                                    builder.append(",");
                                } else {
                                    isFirst[0] = false;
                                }

                                builder.append("{");

                                builder.append("\"_name\":\"");
                                builder.append(resolveName);
                                builder.append("\",");
                                builder.append("\"_value\":");
                                builder.append(JsonBuilder.buildJson(elemType,elem));

                                builder.append("}");
                            }
                        }
                    });
                }

                builder.append("]");
                builder.append("}");
                break;


            case Type.INT_TO_INT_MAP:
                builder.append("\"_type\":");
                builder.append(Type.INT_TO_INT_MAP);
                builder.append(", \"_value\":");
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
                builder.append("\"_type\":");
                builder.append(Type.INT_TO_STRING_MAP);
                builder.append(", \"_value\":");
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
                        builder.append(value);
                    }
                });
                builder.append("}");
                break;

            case Type.INDEX:
                break;

            case Type.KDTREE:
                break;

            case Type.NDTREE:
                break;
        }
        builder.append("}");
        return builder.toString();
    }

    public static Object buildObject(String json, HeapContainer parent) {
        Object obj = null;

        JSONObject jsonObject = new JSONObject(json);
        int type = jsonObject.getInt("_type");

        switch (type) {
            case Type.BOOL:
                obj = jsonObject.getBoolean("_value");
                break;

            case Type.STRING:
                obj = jsonObject.getString("_value");
                break;

            case Type.LONG:
                obj = jsonObject.getLong("_value");
                break;

            case Type.INT:
                obj = jsonObject.getInt("_value");
                break;

            case Type.DOUBLE:
                obj = jsonObject.getDouble("_value");
                break;


            case Type.DOUBLE_ARRAY:
                JSONArray jDArray = jsonObject.getJSONArray("_value");

                double[] darray = new double[jDArray.length()];
                for(int i=0; i < jDArray.length(); i++){
                    darray[i] = jDArray.getDouble(i);
                }

                break;

            case Type.LONG_ARRAY:
                JSONArray jLArray = jsonObject.getJSONArray("_value");

                long[] larray = new long[jLArray.length()];
                for(int i=0; i < jLArray.length(); i++){
                    larray[i] = jLArray.getLong(i);
                }

                break;

            case Type.INT_ARRAY:
                JSONArray jIArray = jsonObject.getJSONArray("_value");

                int[] iarray = new int[jIArray.length()];
                for(int i=0; i < jIArray.length(); i++){
                    iarray[i] = jIArray.getInt(i);
                }

                break;

            case Type.STRING_ARRAY:
                JSONArray jSArray = jsonObject.getJSONArray("_value");

                String[] sarray = new String[jSArray.length()];
                for(int i=0; i < jSArray.length(); i++){
                    sarray[i] = jSArray.getString(i);
                }

                break;

            case Type.LONG_TO_LONG_MAP:
                Map<Long, Long> llMap = new HashMap<>();

                JSONObject llObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysItr = llObject.keys();
                while(keysItr.hasNext()){
                    String key = keysItr.next();

                    Long keyLong = Long.parseLong(key);
                    Long value = llObject.getLong(key);

                    llMap.put(keyLong,value);
                }


                break;

            case Type.LONG_TO_LONG_ARRAY_MAP:
                Map<Long, Long[]> llaMap = new HashMap<>();

                JSONObject llaObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysllaItr = llaObject.keys();
                while(keysllaItr.hasNext()){
                    String key = keysllaItr.next();

                    Long keyLong = Long.parseLong(key);

                    JSONArray array = llaObject.getJSONArray(key);
                    Long[] values = new Long[array.length()];

                    for(int i = 0; i < array.length(); i++){
                        values[i] = array.getLong(i);
                    }

                    llaMap.put(keyLong,values);
                }
                break;

            case Type.STRING_TO_INT_MAP:
                Map<String, Integer> siMap = new HashMap<>();

                JSONObject siObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysSiItr = siObject.keys();
                while(keysSiItr.hasNext()){
                    String key = keysSiItr.next();

                    Integer value = siObject.getInt(key);

                    siMap.put(key,value);
                }
                break;


            case Type.RELATION:
                JSONArray rjArray = jsonObject.getJSONArray("_value");

                Long[] rarray = new Long[rjArray.length()];
                for(int i=0; i < rjArray.length(); i++){
                    rarray[i] = rjArray.getLong(i);
                }

                break;


            case Type.DMATRIX:
                JSONArray dmjArray = jsonObject.getJSONArray("_value");

                int ySize = dmjArray.getJSONArray(0).length();

                Double[][] dmarray = new Double[dmjArray.length()][ySize];

                for(int i=0; i < dmjArray.length(); i++){
                    JSONArray dmjyArray = dmjArray.getJSONArray(i);

                    for(int j = 0 ; j < dmjyArray.length(); j++){
                        dmarray[i][j] = dmjyArray.getDouble(j);
                    }
                }

                break;

            case Type.LMATRIX:
                JSONArray lmjArray = jsonObject.getJSONArray("_value");

                int lySize = lmjArray.getJSONArray(0).length();

                Long[][] lmarray = new Long[lmjArray.length()][lySize];

                for(int i=0; i < lmjArray.length(); i++){
                    JSONArray lmjyArray = lmjArray.getJSONArray(i);

                    for(int j = 0 ; j < lmjyArray.length(); j++){
                        lmarray[i][j] = lmjyArray.getLong(j);
                    }
                }
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

                JSONObject iiObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysIiItr = iiObject.keys();
                while(keysIiItr.hasNext()){
                    String key = keysIiItr.next();

                    Integer iKey = Integer.parseInt(key);
                    Integer value = iiObject.getInt(key);

                    iiMap.put(iKey,value);
                }
                break;

            case Type.INT_TO_STRING_MAP:
                Map<Integer, String> isMap = new HashMap<>();

                JSONObject isObject = jsonObject.getJSONObject("_value");

                Iterator<String> keysIsItr = isObject.keys();
                while(keysIsItr.hasNext()){
                    String key = keysIsItr.next();

                    Integer iKey = Integer.parseInt(key);
                    String value = isObject.getString(key);

                    isMap.put(iKey,value);
                }
                break;

            case Type.INDEX:
                break;

            case Type.KDTREE:
                break;

            case Type.NDTREE:
                break;
        }

        return obj;
    }

}
