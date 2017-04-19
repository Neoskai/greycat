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

package greycat.aerospike;

import com.aerospike.client.*;
import greycat.Callback;
import greycat.Constants;
import greycat.Graph;
import greycat.plugin.Storage;
import greycat.struct.Buffer;
import greycat.struct.BufferIterator;
import greycat.utility.Base64;
import greycat.utility.HashHelper;

import java.util.ArrayList;
import java.util.List;

public class AerospikeDBStorage implements Storage {

    private static final String _connectedError = "PLEASE CONNECT YOUR DATABASE FIRST";


    private AerospikeClient _client;
    private String _address;
    private Integer _port;

    private Graph _graph;
    private boolean _isConnected = false;

    private final List<Callback<Buffer>> updates = new ArrayList<Callback<Buffer>>();


    public AerospikeDBStorage(String address, Integer port){
        _address = address;
        _port = port;
    }

    @Override
    public void get(Buffer keys, Callback<Buffer> callback) {
        //@ TODO  Test in live

        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        Buffer result = _graph.newBuffer();
        BufferIterator it = keys.iterator();
        boolean isFirst = true;

        while (it.hasNext()) {
            Buffer view = it.next();
            try {
                if (!isFirst) {
                    result.write(Constants.BUFFER_SEP);
                } else {
                    isFirst = false;
                }

                Key key = new Key("greycat", "aerospike", view.data());
                Record dataRecorded = _client.get(null, key, "data");


                byte [] res = (byte []) dataRecorded.getValue("data");

                if (res != null) {
                    result.writeAll(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (callback != null) {
            callback.on(result);
        }
    }

    @Override
    public void put(Buffer stream, Callback<Boolean> callback) {
        // @TODO Test in live
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        try{
            Buffer result = null;
            if (updates.size() != 0) {
                result = _graph.newBuffer();
            }

            BufferIterator it = stream.iterator();
            boolean isFirst = true;
            while (it.hasNext()) {
                Buffer keyView = it.next();
                Buffer valueView = it.next();

                if (valueView != null) {
                    Key key = new Key("greycat", "aerospike", keyView.data());
                    Bin data = new Bin("data", valueView.data());
                    _client.put(null, key, data);
                }

                if (result != null) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        result.write(Constants.KEY_SEP);
                    }
                    result.writeAll(keyView.data());
                    result.write(Constants.KEY_SEP);
                    Base64.encodeLongToBuffer(HashHelper.hashBuffer(valueView, 0, valueView.length()), result);
                }
            }

            // @TODO Business Stuff
            for (int i = 0; i < updates.size(); i++) {
                final Callback<Buffer> explicit = updates.get(i);
                explicit.on(result);
            }

            if (callback != null) {
                callback.on(true);
            }
        } catch (Exception e){
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

    @Override
    public void putSilent(Buffer stream, Callback<Buffer> callback) {
        // @TODO
    }

    @Override
    public void remove(Buffer keys, Callback<Boolean> callback) {
        // @TODO
    }

    @Override
    public void connect(Graph graph, Callback<Boolean> callback) {
        if(_isConnected){
            if (callback != null) {
                callback.on(true);
            }
            return;
        }

        _graph = graph;

        try{
            _client = new AerospikeClient(_address,_port);
            _isConnected = true;
            if (callback != null) {
                callback.on(true);
            }

        } catch (AerospikeException e){
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

    @Override
    public void lock(Callback<Buffer> callback) {
        // @TODO
    }

    @Override
    public void unlock(Buffer previousLock, Callback<Boolean> callback) {
        // @TODO
    }

    @Override
    public void disconnect(Callback<Boolean> callback) {
        try{
            _client.close();
            _client = null;
            _isConnected = false;

            if (callback != null) {
                callback.on(true);
            }
        } catch (Exception e){
            e.printStackTrace();

            if (callback != null) {
                callback.on(false);
            }
        }
    }

    @Override
    public void listen(Callback<Buffer> synCallback) {
        updates.add(synCallback);
    }
}
