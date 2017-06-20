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
package greycat.generator;

import greycat.Node;
import greycat.Task;
import greycat.internal.custom.KDTree;
import greycat.internal.custom.NDTree;
import greycat.struct.*;

import java.util.Arrays;
import java.util.List;

public class TypeManager {
    private static List<String> primitives = Arrays.asList("Bool", "String", "Long", "Int", "Double");

    public static String builtInTypeName(String type) {
        String builtInType = null;

        switch (type) {
            case "Bool":
                builtInType = "Type.BOOL";
                break;
            case "String":
                builtInType = "Type.STRING";
                break;
            case "Long":
                builtInType = "Type.LONG";
                break;
            case "Int":
                builtInType = "Type.INT";
                break;
            case "Double":
                builtInType = "Type.DOUBLE";
                break;
            case "DoubleArray":
                builtInType = "Type.DOUBLE_ARRAY";
                break;
            case "LongArray":
                builtInType = "Type.LONG_ARRAY";
                break;
            case "IntArray":
                builtInType = "Type.INT_ARRAY";
                break;
            case "StringArray":
                builtInType = "Type.STRING_ARRAY";
                break;
            case "LongToLongMap":
                builtInType = "Type.LONG_TO_LONG_MAP";
                break;
            case "LongToLongArrayMap":
                builtInType = "Type.LONG_TO_LONG_ARRAY_MAP";
                break;
            case "StringToIntMap":
                builtInType = "Type.STRING_TO_INT_MAP";
                break;
            case "DMatrix":
                builtInType = "Type.DMATRIX";
                break;
            case "LMatrix":
                builtInType = "Type.LMATRIX";
                break;
            case "StructArray":
                builtInType = "Type.STRUCT_ARRAY";
                break;
            case "Struct":
                builtInType = "Type.STRUCT";
                break;
            case "KDTree":
                builtInType = "Type.KDTREE";
                break;
            case "NDTree":
                builtInType = "Type.NDTREE";
                break;
            case "IntToIntMap":
                builtInType = "Type.INT_TO_INT_MAP";
                break;
            case "IntToStringMap":
                builtInType = "Type.INT_TO_STRING_MAP";
                break;
            case "Task":
                builtInType = "Type.TASK";
                break;
            case "TaskArray":
                builtInType = "Type.TASK_ARRAY";
                break;
            case "Node":
                builtInType = "Type.NODE";
                break;
        }

        return builtInType;
    }

    public static String builtInClassName(String type) {
        String className = null;

        switch (type) {
            case "Bool":
                className = boolean.class.getCanonicalName();
                break;
            case "String":
                className = String.class.getCanonicalName();
                break;
            case "Long":
                className = long.class.getCanonicalName();
                break;
            case "Int":
                className = int.class.getCanonicalName();
                break;
            case "Double":
                className = double.class.getCanonicalName();
                break;
            case "DoubleArray":
                className = DoubleArray.class.getCanonicalName();
                break;
            case "LongArray":
                className = LongArray.class.getCanonicalName();
                break;
            case "IntArray":
                className = IntArray.class.getCanonicalName();
                break;
            case "StringArray":
                className = StringArray.class.getCanonicalName();
                break;
            case "LongToLongMap":
                className = LongLongMap.class.getCanonicalName();
                break;
            case "LongToLongArrayMap":
                className = LongLongArrayMap.class.getCanonicalName();
                break;
            case "StringToIntMap":
                className = StringIntMap.class.getCanonicalName();
                break;
            case "DMatrix":
                className = DMatrix.class.getCanonicalName();
                break;
            case "LMatrix":
                className = LMatrix.class.getCanonicalName();
                break;
            case "StructArray":
                className = EGraph.class.getCanonicalName();
                break;
            case "Struct":
                className = ENode.class.getCanonicalName();
                break;
            case "KDTree":
                className = KDTree.class.getCanonicalName();
                break;
            case "NDTree":
                className = NDTree.class.getCanonicalName();
                break;
            case "IntToIntMap":
                className = IntIntMap.class.getCanonicalName();
                break;
            case "IntToStringMap":
                className = IntStringMap.class.getCanonicalName();
                break;
            case "Task":
                className = Task.class.getCanonicalName();
                break;
            case "TaskArray":
                className = Task[].class.getCanonicalName();
                break;
            case "Node":
                className = Node.class.getCanonicalName();
                break;
        }

        return className;
    }

    public static boolean isPrimitive(String type) {
        return primitives.contains(type);
    }


}


