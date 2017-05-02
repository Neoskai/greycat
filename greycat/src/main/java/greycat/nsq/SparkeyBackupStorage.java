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

import com.spotify.sparkey.CompressionType;
import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyWriter;
import greycat.Callback;
import greycat.Graph;
import greycat.struct.Buffer;
import greycat.struct.BufferIterator;

import java.io.File;

public class SparkeyBackupStorage {

    private static final String _connectedError = "PLEASE CONNECT YOUR DATABASE FIRST";
    private static long FLUSHING_CST = 50;

    private String _filePath;
    private boolean _isConnected = false;
    private Graph _graph;

    private long _entries;

    private static SparkeyWriter _writer = null;

    public SparkeyBackupStorage(String filePath) {
        _filePath = filePath;
        _isConnected = false;
        _entries = 0;
    }

    public void connect(Graph graph, Callback<Boolean> callback) {
        _graph = graph;

        File indexFile = new File(_filePath);

        try {
            if (_writer == null){
                if (!indexFile.exists()) {

                    indexFile.mkdirs();
                    //_writer = Sparkey.createNew(indexFile, CompressionType.SNAPPY, 512);
                    _writer = Sparkey.createNew(indexFile);
                    _writer.flush();
                    _writer.writeHash();
                    _writer.close();
                }

                _writer = Sparkey.append(indexFile);
            }

            _isConnected = true;
            if (callback != null) {
                callback.on(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

    public void putAndFlush(Buffer stream, Callback<Boolean> callback){
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        try {
            BufferIterator it = stream.iterator();
            while (it.hasNext()) {
                Buffer keyView = it.next();
                Buffer valueView = it.next();

                StorageKeyChunk key = StorageKeyChunk.build(keyView);
                StorageValueChunk value = StorageValueChunk.build(valueView);

                System.out.println("Received key is : " + key.toString());
                System.out.println("Received data is : " + value.toString());

                if (valueView != null) {
                    // When saving key to base64 format
                    // _writer.put(keyView.data(), valueView.data());

                    // When saving key to string format with ; separator
                    _writer.put(key.buildString().getBytes(), valueView.data());
                }
                _writer.flush();
                _entries++;

                if(_entries % FLUSHING_CST == 0){
                    _writer.writeHash();
                }
            }

            if (callback != null) {
                callback.on(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

}
