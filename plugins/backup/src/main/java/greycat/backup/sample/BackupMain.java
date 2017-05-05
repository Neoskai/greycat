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
package greycat.backup.sample;


import greycat.Graph;
import greycat.backup.BackupLoader;
import greycat.websocket.WSServer;

public class BackupMain {
    public static void main(String[] args) {
        BackupLoader loader = new BackupLoader("data/save_1_0.spi");
        loader.load();
        //loader.run();
        loader.logRun();

        Graph g = loader.buildFromLogs();
        System.out.println("Rebuilded following graph: " + g);

        WSServer serverDebug = new WSServer(g,8050);
        serverDebug.start();
    }
}
