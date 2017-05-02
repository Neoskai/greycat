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

import com.spotify.sparkey.Sparkey;
import com.spotify.sparkey.SparkeyLogIterator;
import com.spotify.sparkey.SparkeyReader;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.nsq.StorageKeyChunk;
import greycat.nsq.StorageValueChunk;
import greycat.struct.Buffer;

import java.io.File;
import java.io.IOException;

public class BackupLoader {
    private SparkeyReader _reader;
    private Graph _graph;
    private String _filePath;
    private File _indexFile;

    private boolean _isLoaded = false;

    public BackupLoader(String filePath){
        _filePath = filePath;
        _graph = GraphBuilder.newBuilder().build();
    }

    public void load(){
        try {
            _indexFile = new File(_filePath);

            if (!_indexFile.exists()){
                System.err.println("File does not exist");
            }

            _reader = Sparkey.open(_indexFile);
            _isLoaded = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads entries from the log file
     */
    public void logRun(){
        if(!_isLoaded){
            System.err.println("Not connected");
            return;
        }

        try {
            SparkeyLogIterator logIterator = new SparkeyLogIterator(Sparkey.getLogFile(_indexFile));

            for (SparkeyReader.Entry entry : logIterator) {
                if (entry.getType() == SparkeyReader.Type.PUT) {
                    Buffer buffer = _graph.newBuffer();
                    buffer.writeAll(entry.getKey());
                    StorageKeyChunk key = StorageKeyChunk.build(buffer);

                    buffer.free();
                    buffer.writeAll(entry.getValue());
                    StorageValueChunk value = StorageValueChunk.build(buffer);
                    buffer.free();

                    System.out.println("Key is: "+ key + " with value " + value);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads entries from the data file
     * Non working version
     */
    public void run(){
        if(!_isLoaded){
            System.err.println("Not connected");
            return;
        }

        try{
            for (SparkeyReader.Entry entry : _reader) {
                Buffer buffer = _graph.newBuffer();
                buffer.writeAll(entry.getKey());
                StorageKeyChunk key = StorageKeyChunk.build(buffer);

                buffer.free();
                buffer.writeAll(entry.getValue());
                StorageValueChunk value = StorageValueChunk.build(buffer);
                buffer.free();

                System.out.println("Key is: "+ key + " with value " + value);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
