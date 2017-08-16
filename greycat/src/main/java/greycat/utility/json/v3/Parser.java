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

import greycat.Graph;
import greycat.Node;
import greycat.struct.Buffer;
import greycat.utility.json.v2.TypedObject;

import java.util.LinkedList;

public class Parser {

    private byte RECORD_BUILD = 1;
    private byte NODE_BUILD = 2;
    private byte VALUE_BUILD = 3;
    private byte INDEX_BUILD = 4;

    private Graph _graph;

    public Parser(Graph g){
        _graph = g;
    }

    public void parse(Buffer buffer){
        // Tokenize the buffer
        BufferIndex index = new BufferIndex((int) buffer.length());
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.parse(buffer,index);

        byte state = -1;

        LinkedList<TypedObject> parents = new LinkedList<>();

        for(int i = 0; i < index.size; i++){
            int type = index.type[i];
            int length = index.length[i];
            int start = index.start[i];


        }
    }
}
