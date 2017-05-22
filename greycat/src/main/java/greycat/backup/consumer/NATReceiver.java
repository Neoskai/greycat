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

package greycat.backup.consumer;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.tools.StorageHandler;
import greycat.struct.Buffer;
import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;

/**
 * @ignore ts
 */
public class NATReceiver {

    public static void main(String[] args )
    {
        try {
            Connection nc = Nats.connect();

            Graph localGraph = GraphBuilder.newBuilder().build();

            StorageHandler storageHandler = new StorageHandler();
            storageHandler.load();

            nc.subscribe("Greycat", m -> {
                Buffer buffer = localGraph.newBuffer();
                buffer.writeAll(m.getData());

                try {
                    storageHandler.process(buffer);
                } catch (Exception e) {
                    try {
                        nc.publish("Greycat", m.getData());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            });

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    storageHandler.close();
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
