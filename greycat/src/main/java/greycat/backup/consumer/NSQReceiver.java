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

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.struct.Buffer;

public class NSQReceiver {


    public static void main(String[] args )
    {
        NSQLookup lookup = new DefaultNSQLookup();
        lookup.addLookupAddress("localhost", 4161);

        Graph localGraph = GraphBuilder.newBuilder().build();

        StorageHandler storageHandler = new StorageHandler();
        storageHandler.load();

        NSQConsumer consumer = new NSQConsumer(lookup, "Greycat", "MyChannel", (message) -> {
            try {
                Buffer buffer = localGraph.newBuffer();
                buffer.writeAll(message.getMessage());

                storageHandler.process(buffer);

                message.finished();

            } catch (Exception e){
                e.printStackTrace();
                message.requeue();
            }
        });

        consumer.start();
    }
}
