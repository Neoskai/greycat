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

/**
 * Add Maven dependency to use:

 <dependency>
 <groupId>io.nats</groupId>
 <artifactId>java-nats-streaming</artifactId>
 <version>${natstream.version}</version>
 </dependency>

 <natstream.version>0.4.1</natstream.version>

 * And change client names between receiver and emitter.
 * This is a first implementation of Sender / Receiver using NATS Streaming, incomplete
 * (Timer error due to NATS Streaming when using with large number of messages + Not handling client name)
 */

/*


package greycat.backup.external;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.tools.StorageHandler;
import greycat.struct.Buffer;

import io.nats.stan.Connection;
import io.nats.stan.ConnectionFactory;

import java.io.IOException;

public class NATStreamingReceiver {

    public static void main(String[] args )
    {
        try {
            ConnectionFactory cf = new ConnectionFactory("test-cluster","client2");
            Connection nc = cf.createConnection();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

*/
