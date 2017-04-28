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

package greycat.aerospike;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Node;
import greycat.Type;
import org.junit.Test;

public class MinimalTest {

    @Test
    public void minimalTest(){
        Graph g = new GraphBuilder()
                .withStorage(new AerospikeDBStorage("localhost", 3000, "test"))
                .withMemorySize(200000)
                .build();

        g.connect(isConnected -> {
            System.out.println("Connected : " + isConnected);

            Node sensor0 = g.newNode(0, 0);
            sensor0.set("sensorId", Type.INT, 12);
            sensor0.set("name", Type.STRING, "sensor0");

            Node room0 = g.newNode(0, 0);
            room0.set("name", Type.STRING, "room0");
            room0.addToRelation("sensors", sensor0);

            g.disconnect(result -> {
                System.out.println("End");
            });

        });
    }
}