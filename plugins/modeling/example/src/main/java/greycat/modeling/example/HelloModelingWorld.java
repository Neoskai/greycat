package greycat.modeling.example;

import greycat.Callback;
import greycat.Graph;
import greycat.GraphBuilder;
import modeling.Device;
import modeling.ModelingPlugin;

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
public class HelloModelingWorld {


    public static void main(String[] args) {
        GraphBuilder builder = new GraphBuilder()
                .withPlugin(new ModelingPlugin());
        Graph graph = builder.build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                Device device = (Device) graph.newTypedNode(0, 0, Device.NODE_NAME);
                device.setName("addsf");
                device.setIdentifier(3);

                Device device1 = graph.newTypedNode(0, 0, Device.NODE_NAME, Device.class);
                Device device2 = Device.create(0, 0, graph);

            }
        });
    }
}

