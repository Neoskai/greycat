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

import greycat.Graph;
import greycat.Node;
import greycat.Type;
import greycat.struct.*;

import java.util.*;

import static greycat.utility.json.JsonConst.*;

/**
 * @ignore ts
 */
public class JsonParser {
    private LinkedList _list;
    private Graph _graph;

    private Node current;

    private long _cursor;

    public JsonParser(Graph g){
        _list = new LinkedList();
        _graph = g;
        current = null;
    }

    public void buildGraph(Buffer buffer, long start){
        _cursor = start;
        long end = buffer.length();

        while(_cursor != end){
            byte current = buffer.read(_cursor);
            if(current == ARRS){
                _list.add(current);
            }
            if(current == OBJS){
                _list.add(current);
                _cursor++;
                buildRecord(buffer);
            }

            _cursor++;
        }

        if(_list.size() != 0){
            System.err.println("Stack is not empty at the end of read");
        }
    }

    public void buildRecord(Buffer buffer){
        long end = buffer.length();

        while(_cursor != end){
            String key = nextKey(buffer);
            _cursor++;
            switch(key){
                case "world":
                    long world = getLong(buffer);
                    System.out.println("World is : " + world);
                    break;
                case "id":
                    long id = getLong(buffer);
                    System.out.println("Id is : " + id);
                    break;
                case "nodetype":
                    String type = getString(buffer);
                    System.out.println("Type is : " + type);
                    break;

                //case "value":
                case "values":
                    Object[] values = getValues(buffer);
                    break;

                case "time":
                case "times":
                    long times[] = getLArray(buffer);
                    System.out.println("Times retreived, with length: " + times.length);
                    break;
            }



            /*byte current = buffer.read(_cursor);

            if(current == ARRE || current == OBJE){
                byte out = (byte) _list.pop();

                if(current == ARRE && out != ARRS){
                    System.err.println("Out of ARRE and in is not ARRS");
                }
                if(current == OBJE && out !=  OBJS){
                    System.err.println("Out of OBJE and in is not OBJS");
                }

            }

            _cursor++;
            */
        }

    }

    public Object[] getValues(Buffer buffer){
        List<Object> values = new ArrayList<>();

        System.out.println("Opening values: " + buffer.read(_cursor));
        _cursor++;
        System.out.println("Second values: " + buffer.read(_cursor));

        while(buffer.read(_cursor) != ARRE){
            Object value = getValue(buffer);
            values.add(value);
            _cursor++;
        }

        return values.toArray();
    }

    private boolean getBoolean(Buffer buffer){
        long start = _cursor;

        while(buffer.read(_cursor) != ARRE){
            _cursor++;
        }

        byte[] stringBytes = buffer.slice(start+1, _cursor -2);
        _cursor++;

        return Boolean.valueOf(new String(stringBytes));
    }

    public Object getValue(Buffer buffer){
        if(buffer.read(_cursor) == OBJE || buffer.read(_cursor) == ARRE){
            return null;
        }
        _cursor++;
        System.out.println(buffer.read(_cursor) + " is displayed");
        int type = getInt(buffer);
        System.out.println("Type is: " + type);

        _cursor++;

        switch(type){
            case Type.BOOL:
                return getBoolean(buffer);

            case Type.STRING:
                return getString(buffer);

            case Type.LONG:
                return getLong(buffer);

            case Type.INT:
                return getInt(buffer);

            case Type.DOUBLE:
                break;


            case Type.DOUBLE_ARRAY:
                break;

            case Type.LONG_ARRAY:
                break;

            case Type.INT_ARRAY:
                break;

            case Type.STRING_ARRAY:
                break;

            case Type.LONG_TO_LONG_MAP:
                break;

            case Type.LONG_TO_LONG_ARRAY_MAP:
                break;

            case Type.STRING_TO_INT_MAP:
                break;


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
                return getNodeValues(buffer);

            case Type.INT_TO_INT_MAP:
                break;

            case Type.INT_TO_STRING_MAP:
                break;

            case Type.INDEX:
                break;

            case Type.KDTREE:
                break;

            case Type.NDTREE:
                break;
        }

        while(buffer.read(_cursor) != ARRE){
            Object value = getValue(buffer);
            //values.add(value);
        }


        return null;
    }

    private Object[] getNodeValues(Buffer buffer){
        while(buffer.read(_cursor) != OBJE){
            String key = nextKey(buffer);
            if(key == null){
                _cursor++;
                return null;
            }
            System.out.println("Found key in node: " + key);

            getValue(buffer);
        }
        return null;
    }

    public long[] getLArray(Buffer buffer){
        List<Long> elems = new ArrayList<>();

        _cursor++;

        while(buffer.read(_cursor) != ARRE){
            long elem = getLong(buffer);
            elems.add(elem);
        }

        return elems.stream().mapToLong(l -> l).toArray();
    }

    private long getLong(Buffer buffer){
        long start = _cursor;

        while(buffer.read(_cursor) != SEP && buffer.read(_cursor) != ARRE){
            _cursor++;
        }


        byte[] longBytes = buffer.slice(start, _cursor -1);
        if(buffer.read(_cursor) == ARRE){
            _cursor--;
        }
        _cursor++;

        return Long.valueOf(new String(longBytes));
    }

    private int getInt(Buffer buffer){
        long start = _cursor;

        while(buffer.read(_cursor) != SEP && buffer.read(_cursor) != ARRE){
            System.out.println(buffer.read(_cursor));
            _cursor++;
        }

        byte[] longBytes = buffer.slice(start, _cursor -1);
        if(buffer.read(_cursor) == ARRE){
            _cursor--;
        }
        _cursor++;
        System.out.println("After read int, value is: " + buffer.read(_cursor) + " And cursor " + _cursor);

        return Integer.valueOf(new String(longBytes));
    }

    private double getDouble(Buffer buffer){
        long start = _cursor;

        while(buffer.read(_cursor) != SEP && buffer.read(_cursor) != ARRE){
            _cursor++;
        }


        byte[] doubleBytes = buffer.slice(start, _cursor -1);
        if(buffer.read(_cursor) == ARRE){
            _cursor--;
        }
        _cursor++;

        return Double.valueOf(new String(doubleBytes));
    }

    private String getString(Buffer buffer){
        long start = _cursor;

        while(buffer.read(_cursor) != SEP && buffer.read(_cursor) != ARRE){
            _cursor++;
        }

        byte[] stringBytes = buffer.slice(start+1, _cursor -2);
        _cursor++;

        return new String(stringBytes);
    }

    private String nextKey(Buffer buffer){
        while(buffer.read(_cursor) != TEXT && buffer.read(_cursor) != OBJE){
            _cursor++;
        }
        if(buffer.read(_cursor) == OBJE){
            return null;
        }

        _cursor++;
        long start = _cursor;

        while(buffer.read(_cursor) != TEXT){
            _cursor++;
        }
        _cursor++;

        byte[] key = buffer.slice(start, _cursor-2);

        System.out.println(new String(key));

        return new String(key);
    }

    private char getChar(byte b){
        return (char) (b & 0xFF);
    }
}
