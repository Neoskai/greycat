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

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @ignore ts
 */
public class UTTSender extends AbstractSender{

    private MqttClient _producer;

    // List of public Brokers here:
    // https://github.com/mqtt/mqtt.github.io/wiki/public_brokers

    public UTTSender(){
        try {
            _producer = new MqttClient(
                    "tcp://broker.mqttdashboard.com:1883", //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()); //Persistence

            _producer.connect();
            _isConnected = true;
        } catch (MqttException e) {
            _isConnected = false;
            System.err.println(e);
        }
    }

    @Override
    public boolean sendMessage(byte[] message) {
        try {
            _producer.publish("Greycat", message, 2, false);

            return true;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return false;
    }
}
