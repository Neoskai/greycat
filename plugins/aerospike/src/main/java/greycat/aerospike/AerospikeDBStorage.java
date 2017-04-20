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
import com.aerospike.client.policy.WritePolicy;
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
    private static final byte[] _prefixKey = "prefix".getBytes();


    private AerospikeClient _client;
    private String _address;
    private Integer _port;
    private String _namespace;

    private Graph _graph;
    private boolean _isConnected = false;

    private final List<Callback<Buffer>> updates = new ArrayList<Callback<Buffer>>();


    public AerospikeDBStorage(String address, Integer port, String namespace){
        _address = address;
        _port = port;
        _namespace = namespace;
    }

    @Override
    public void get(Buffer keys, Callback<Buffer> callback) {
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

                Key key = new Key(_namespace, "greycat", view.data());
                Record dataRecorded = _client.get(null, key, "data");

                byte [] res;
                try{
                    res = (byte []) dataRecorded.getValue("data");
                } catch (NullPointerException e){
                    res = null;
                }

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
                    Key key = new Key(_namespace, "greycat", keyView.data());
                    Bin data = new Bin("data", valueView.data());

                    //System.out.printf("Initiating new Key: " + keyView.data());
                    //System.out.println(" with data " + data.value);
                    //System.out.println("Key is " + Base64.decodeToLongWithBounds(keyView, 0, keyView.length()));

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
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        try{
            Buffer result = _graph.newBuffer();
            BufferIterator it = stream.iterator();
            boolean isFirst = true;

            while (it.hasNext()) {
                Buffer keyView = it.next();
                Buffer valueView = it.next();

                if (valueView != null) {
                    Key key = new Key(_namespace, "greycat", keyView.data());
                    Bin data = new Bin("data", valueView.data());
                    _client.put(null, key, data);
                }

                if (isFirst) {
                    isFirst = false;
                } else {
                    result.write(Constants.KEY_SEP);
                }
                result.writeAll(keyView.data());
                result.write(Constants.KEY_SEP);
                Base64.encodeLongToBuffer(HashHelper.hashBuffer(valueView, 0, valueView.length()), result);

            }

            for (int i = 0; i < updates.size(); i++) {
                final Callback<Buffer> explicit = updates.get(i);
                explicit.on(result);
            }

            callback.on(result);

        } catch (Exception e){
            e.printStackTrace();
            if (callback != null) {
                callback.on(null);
            }
        }
    }

    @Override
    public void remove(Buffer keys, Callback<Boolean> callback) {
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        try {
            BufferIterator it = keys.iterator();
            while (it.hasNext()) {
                Buffer view = it.next();
                Key rmKey = new Key(_namespace, "greycat", view.data());
                _client.delete(new WritePolicy(), rmKey);
            }
            if (callback != null) {
                callback.on(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
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
        Key key = new Key(_namespace, "greycat", _prefixKey);
        Record dataRecorded = _client.get(null, key, "data");

        byte[] current;
        try {
            current = (byte[]) dataRecorded.getValue("data");
        } catch (NullPointerException e){
            current = null;
        }

        if (current == null) {
            current = new String("0").getBytes();
        }

        Short currentPrefix = Short.parseShort(new String(current));
        Bin data = new Bin("data", ((currentPrefix + 1) + "").getBytes());

        _client.put(null, key, data);
        if (callback != null) {
            Buffer newBuf = _graph.newBuffer();
            Base64.encodeIntToBuffer(currentPrefix, newBuf);
            callback.on(newBuf);
        }

    }

    @Override
    public void unlock(Buffer previousLock, Callback<Boolean> callback) {
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        callback.on(true);
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
