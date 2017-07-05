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

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.struct.Buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static greycat.Constants.BUFFER_SEP;

/**
 * @ignore ts
 */
public class LogSender implements Runnable {
    private AbstractSender _sender;

    public LogSender(AbstractSender sender){
        _sender = sender;
    }

    @Override
    public void run() {
        try {
            String tempString = _sender.logsFile() + "_send";
            File tempFile = new File(tempString);

            FileInputStream input = new FileInputStream(tempString);

            int content;
            Graph g = GraphBuilder.newBuilder().build();
            Buffer buffer = g.newBuffer();

            while ((content = input.read()) != -1) {
                if (content == BUFFER_SEP) {
                    _sender.sendMessage("Greycat", buffer.data());
                    buffer.free();
                } else {
                    buffer.write((byte) content);
                }
            }

            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
