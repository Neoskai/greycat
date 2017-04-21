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

package com.datathings;

import greycat.*;
import greycat.struct.Buffer;
import greycat.struct.BufferIterator;
import greycat.utility.BufferView;
import greycat.utility.Tuple;
import greycat.utility.VerbosePlugin;
import org.junit.Assert;
import org.junit.Test;

import static greycat.Tasks.newTask;
import static greycat.internal.task.CoreActions.*;


public class NSQHookTest {

    @Test
    public void test() {

        Graph g = new GraphBuilder()
                .withPlugin(new NSQPlugin())
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
                System.out.println("End of test");
            });

        });
    }

    private String _address = "localhost";
    private int _port = 4150;

    private NSQSender _sender;

    @Test
    public void manualTest(){
        Graph g = GraphBuilder.newBuilder().build();
        //Graph g = GraphBuilder.newBuilder().withPlugin(new VerbosePlugin()).build();
        g.connect(result -> {

            int[] count = new int[1];
            count[0] = 0;

            newTask()
                    .addHook(new TaskHook() {
                        @Override
                        public void start(TaskContext initialContext) {
                            System.out.println("Hook start");
                            _sender = new NSQSender(_address, _port);
                        }

                        @Override
                        public void beforeAction(Action action, TaskContext context) {
                            //_sender.sendMessage("Act:"+action.name()+":"+context.resultAsStrings());

                            Buffer buffer = context.graph().newBuffer();
                            action.serialize(buffer);
                            byte[] command = buffer.data();

                            _sender.sendMessage(command);
                        }

                        @Override
                        public void afterAction(Action action, TaskContext context) {

                        }

                        @Override
                        public void beforeTask(TaskContext parentContext, TaskContext context) {
                            _sender.sendMessage("Task:"+context.template("name")+":"+context.resultAsStrings());

                        }

                        @Override
                        public void afterTask(TaskContext context) {
                            //System.out.println("After task hook");
                        }

                        @Override
                        public void end(TaskContext finalContext) {
                            System.out.println("Hook end");
                        }
                    })
                    .then(createNode())
                    .then(setAttribute("name", Type.STRING, "House"))
                    .then(setAttribute("temp", Type.DOUBLE, "21.1"))
                    .then(createNode())
                    .then(setAttribute("name", Type.STRING, "Room"))
                    .then(setAttribute("temp", Type.STRING, "23.2"))
                    //.then(readVar("House"))
                    //.then(addVarToRelation("rooms", "Room"))
                    .then(travelInTime("10"))
                    .then(setAttribute("temp", Type.DOUBLE, "15.3"))
                    //.then(travelInWorld("1"))
                    .then(travelInTime("20"))
                    .then(setAttribute("temp", Type.DOUBLE, "-2.5"))
                    .then(travelInTime("100"))
                    .then(setAttribute("temp", Type.DOUBLE, "-18.5"))
                    .execute(g, null);

        });
    }
}
