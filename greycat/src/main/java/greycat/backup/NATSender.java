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

package greycat.backup;

import greycat.BackupOptions;
import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;

/**
 * @ignore ts
 */
public class NATSender extends AbstractSender{

    private Connection _producer;

    public NATSender(){
        this(true);
    }

    public NATSender(boolean directSend){
        super(directSend);
        try {
            String url = BackupOptions.natsServer();
            _producer = Nats.connect(url);
            _isConnected = true;
        } catch (IOException e) {
            _isConnected = false;
            System.err.println(e);
        }
    }

    @Override
    public boolean sendMessage(String channel, byte[] message){
        try {
            _producer.publish(channel, message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
