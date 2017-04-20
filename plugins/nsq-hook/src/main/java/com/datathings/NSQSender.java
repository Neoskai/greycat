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

import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;

import java.util.concurrent.TimeoutException;

public class NSQSender {

    private NSQProducer _producer;

    public NSQSender(String address, int port){
        _producer = new NSQProducer().addAddress(address,port).start();
    }

    /**
     * Sending a message to the Greycat topic of our NSQ server
     * @param message String message
     * @return True if success to send, false otherwise
     */
    public boolean sendMessage(String message){
        try {
            _producer.produce("Greycat", message.getBytes());
            return true;
        } catch (NSQException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sending a message to the Greycat topic of our NSQ server
     * @param message byte[] message
     * @return True if success , false otherwise
     */
    public boolean sendMessage(byte[] message){
        try {
            _producer.produce("Greycat", message);
            return true;
        } catch (NSQException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return false;
    }


}
