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

import greycat.Type;
import greycat.struct.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @ignore ts
 */
public class JsonBuilder {

    public static String buildObject(int type, Object elem) {
        final boolean[] isFirst = {true};
        final StringBuilder builder = new StringBuilder();

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

            case Type.INT_TO_INT_MAP:
                builder.append("\"_type\":");
                builder.append(Type.INT_TO_INT_MAP);
                builder.append(", \"_value\":");
                builder.append("{");
                IntStringMap castedMapI2I = (IntStringMap) elem;
                isFirst[0] = true;
                castedMapI2I.each(new IntStringMapCallBack() {
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


        }

        return builder.toString();
    }
}
