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

import java.io.*;

import static greycat.Constants.BUFFER_SEP;
import static greycat.Constants.CHUNK_ESEP;

/**
 * @ignore ts
 */
public abstract class AbstractSender {
    private Graph g = null;
    protected boolean _isConnected;
    protected boolean _directSend;

    private FileOutputStream stream;
    private int _logFileId;

    private final Object _LOCK = new Object();

    protected AbstractSender(boolean directSend){
        connect();
        _directSend = directSend;
        _logFileId = 0;
    }

    /**
     * Sending a message to the Greycat topic of our NSQ server
     * @param message byte[] message
     * @return True if success , false otherwise
     */
    public abstract boolean sendMessage(String channel, byte[] message);

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
    public Buffer bufferizeMessage(long world, long time, long id, int index, long eventId, int type, Object value){
        if (g == null){ // Init done here to prevent error caused by Class Init Order
            g = GraphBuilder.newBuilder().build();
        }

        Buffer buffer = g.newBuffer();

        Base64.encodeLongToBuffer(id, buffer);
        buffer.write(Constants.KEY_SEP);
        Base64.encodeLongToBuffer(eventId, buffer);
        buffer.write(BUFFER_SEP);

        Base64.encodeLongToBuffer(world, buffer);
        buffer.write(Constants.CHUNK_SEP);
        Base64.encodeLongToBuffer(time, buffer);
        buffer.write(Constants.CHUNK_SEP);
        Base64.encodeIntToBuffer(index, buffer);
        buffer.write(Constants.CHUNK_SEP);

        if(value == null){
            Base64.encodeIntToBuffer(Type.REMOVE, buffer);
            buffer.write(Constants.CHUNK_SEP);
        }else{
            Base64.encodeIntToBuffer(type, buffer);
            buffer.write(Constants.CHUNK_SEP);
            valueToBuffer(buffer,value, type);
        }

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
    public void processMessage(long world, long time, long id, int index, long eventId, int type, Object value){
        Buffer buffer = bufferizeMessage(world, time, id, index, eventId, type, value);

        if(_directSend) {
            switch (type) {
                case Type.RELATION:
                case Type.REMOVERELATION:
                    sendMessage("Relation", buffer.data());
                    break;
                default:
                    sendMessage("Greycat", buffer.data());
            }
        } else {
            synchronized (_LOCK) {
                try {
                    stream.write(buffer.data());
                    stream.write(CHUNK_ESEP);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
    }

    /**
     * Sends all the local logs to the server on save and then deletes the temp file
     */
    public void sendLocals(){
        if (g == null){ // Init done here to prevent error caused by Class Init Order
            g = GraphBuilder.newBuilder().build();
        }

        if(_directSend){
            return;
        }
        String tempString = logsFile() + "_send" + _logFileId++;
        File file = new File(logsFile());
        File tempFile = new File(tempString);


        synchronized (_LOCK) {
            disconnect();
            file.renameTo(tempFile);
            connect();

            LogSender sender = new LogSender(this, tempString);
            sender.run();
        }

    }

    public void connect(){
        try {
            File newFile = new File(logsFile());
            if(newFile.exists()){
                newFile.delete();
            }

            stream = new FileOutputStream(logsFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the file where logs are locally saved when in non direct sending mode
     * @return String containing the path
     */
    public String logsFile(){
        String dir = (String) System.getProperties().get("user.dir");
        dir += "/tempLogs";
        return dir;
    }

    /**
     * (INCOMPLETE) PRIMITIVE TYPES ONLY
     * Writes data to buffer using Base64 writing
     * @param buffer The buffer to write in
     * @param obj The object to write
     * @param type The type of the object
     * @return The buffer with the object written in
     */
    private static Buffer valueToBuffer(Buffer buffer, Object obj, int type){
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
                buffer.writeAll(serialize(obj));
        }
        return buffer;
    }

    public boolean isConnected(){
        return _isConnected;
    }

    /**
     * Serialize object to byte array
     * @param obj Object to serialize
     * @return Byte array containing the object
     */
    private static byte[] serialize(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;

        try {
            os = new ObjectOutputStream(out);
            os.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
