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

package greycat.nsq;

import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;
import greycat.Constants;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Type;
import greycat.struct.Buffer;
import greycat.utility.Base64;

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

    /**
     * Parses a message
     * @param world Current world
     * @param time Current time
     * @param id Current id
     * @param index Id of setter
     * @param type Type of value
     * @param value Value
     * @return
     */
    public String parseMessage(long world, long time, long id, int index, byte type, Object value){

        String base = "";

        String KEYSEP = ";";
        String VALUESEP = "#";

        base += world + KEYSEP +
                time + KEYSEP
                + id + KEYSEP
                + index + KEYSEP
                + type + VALUESEP
                + value.toString();


        System.out.println("Parsed message: " + base);
        return base;
    }

    /**
     * Bufferize a message
     * @param world Current world
     * @param time Current time
     * @param id Current id
     * @param index Id of setter
     * @param type Type of value
     * @param value Value
     * @return Buffer containing key and value
     */
    public Buffer bufferizeMessage(long world, long time, long id, int index, byte type, Object value){
        Graph g = GraphBuilder.newBuilder().build();
        Buffer buffer = g.newBuffer();

        Base64.encodeLongToBuffer(world, buffer);
        buffer.write(Constants.KEY_SEP);
        Base64.encodeLongToBuffer(time, buffer);
        buffer.write(Constants.KEY_SEP);
        Base64.encodeLongToBuffer(id, buffer);
        buffer.write(Constants.KEY_SEP);
        Base64.encodeIntToBuffer(index, buffer);

        buffer.write(Constants.BUFFER_SEP);
        buffer.write(type);
        buffer.write(Constants.CHUNK_SEP);
        buffer.writeAll(serialize(value, type));

        return buffer;
    }

    /**
     * Serialize abstract objects to byte array so we can send them through the buffer
     * @param obj Object to serialize
     * @return Byte array containing the object
     */
    public static byte[] serialize(Object obj, byte type){
        switch (type){
            case Type.STRING:
                return ((String) obj).getBytes();
        }
        return null;
    }

}
