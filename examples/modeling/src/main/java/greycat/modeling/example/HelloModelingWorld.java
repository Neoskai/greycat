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
package greycat.modeling.example;

import greycat.Callback;
import greycat.Graph;
import greycat.GraphBuilder;
import model.Device;
import model.ModelPlugin;
import model.Module;

public class HelloModelingWorld {


    public static void main(String[] args) {
        //add  the generated plugin to a GraphBuilder
        GraphBuilder builder = new GraphBuilder().withPlugin(new ModelPlugin());
        //build the graph
        Graph graph = builder.build();
        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                //Method 1
                Device device = (Device) graph.newTypedNode(0, 0, Device.NODE_NAME);
                device.setName("device");
                device.setIdentifier(3);
                //Method 2
                Device device2 = graph.newTypedNode(0, 0, Device.NODE_NAME, Device.class);
                device2.setName("device2");
                //Method 3
                Device device3 = Device.create(0, 0, graph);
                device3.setName("device3");

                // module
                Module module = (Module) graph.newTypedNode(0, 0, Module.NODE_NAME);
                module.setName("name");
                module.setName2("name2");
                device.addToModules(module);

                System.out.println(device);
                System.out.println(device2);
                System.out.println(device3);

                device.findModules(result1 -> System.out.println(result1), "name", "name2");

            }
        });
    }
}

