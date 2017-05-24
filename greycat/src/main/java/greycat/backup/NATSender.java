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

import greycat.Constants;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Type;
import greycat.struct.Buffer;
import greycat.utility.Base64;
import io.nats.client.Connection;
import io.nats.client.Nats;

import java.io.IOException;


/**
 * @ignore ts
 */
public class NATSender {

    private Graph g = null;
    private Connection _producer;

    private boolean _isConnected;

    public NATSender(){
        try {
            _producer = Nats.connect();
            _isConnected = true;
        } catch (IOException e) {
            _isConnected = false;
            System.err.println(e);
        }
    }

    /**
     * Sending a message to the Greycat topic of our NSQ server
     * @param message byte[] message
     * @return True if success , false otherwise
     */
    public boolean sendMessage(byte[] message){
        try {
            _producer.publish("Greycat", message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
    public Buffer bufferizeMessage(long world, long time, long id, int index, long eventId, byte type, Object value){
        if (g == null){
            g = GraphBuilder.newBuilder().build();
        }

        Buffer buffer = g.newBuffer();

        Base64.encodeLongToBuffer(id, buffer);
        buffer.write(Constants.KEY_SEP);
        Base64.encodeLongToBuffer(eventId, buffer);
        buffer.write(Constants.BUFFER_SEP);

        Base64.encodeLongToBuffer(world, buffer);
        buffer.write(Constants.CHUNK_SEP);
        Base64.encodeLongToBuffer(time, buffer);
        buffer.write(Constants.CHUNK_SEP);
        Base64.encodeIntToBuffer(index, buffer);
        buffer.write(Constants.CHUNK_SEP);

        if(value == null){
            buffer.write(Type.REMOVE);
            buffer.write(Constants.CHUNK_SEP);
        }else{
            buffer.write(type);
            buffer.write(Constants.CHUNK_SEP);
            valueToBuffer(buffer,value, type);
        }

        // To use if wanting to use byte format for values instead of Base64
        //buffer.write(Constants.CHUNK_SEP);
        //buffer.writeAll(serialize(value, type));

        return buffer;
    }

    /**
     * Bufferize and send a message to the NATS Server
     * @param world Current world
     * @param time Time to edit
     * @param id Id of the node
     * @param index Index of the element
     * @param eventId ID of the event
     * @param type The type of the value we are setting
     * @param value The value to set
     */
    public void processMessage(long world, long time, long id, int index, long eventId, byte type, Object value){
        Buffer buffer = bufferizeMessage(world, time, id, index, eventId, type, value);
        sendMessage(buffer.data());

        /* executor.submit(() -> {
            Buffer buffer = bufferizeMessage(world, time, id, index, eventId, type, value);
            sendMessage(buffer.data());
        });*/
    }

    /**
     * (INCOMPLETE) PRIMITIVE TYPES ONLY
     * Writes data to buffer using Base64 writing
     * @param buffer The buffer to write in
     * @param obj The object to write
     * @param type The type of the object
     * @return The buffer with the object written in
     */
    private static Buffer valueToBuffer(Buffer buffer, Object obj, byte type){
        if (obj == null){
            return buffer;
        }

        switch (type){
            case Type.STRING:
                Base64.encodeStringToBuffer((String) obj, buffer);
                break;
            case Type.BOOL:
                buffer.write((byte) ((boolean) obj?1:0));
                break;
            case Type.LONG:
                Base64.encodeLongToBuffer((long) obj, buffer);
                break;
            case Type.INT:
                Base64.encodeIntToBuffer((int) obj, buffer);
                break;
            case Type.DOUBLE:
                Base64.encodeDoubleToBuffer((double) obj, buffer);
                break;
            default:
                buffer.writeAll((byte[]) obj);
        }
        return buffer;
    }

    public boolean isConnected(){
        return _isConnected;
    }
}
