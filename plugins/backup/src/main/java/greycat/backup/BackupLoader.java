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

import greycat.Callback;
import greycat.Graph;
import greycat.GraphBuilder;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BackupLoader {
    private static final int POOLSIZE = 5;

    private Graph _graph;
    private String _folderPath;

    private HashMap<Long, Integer> nodes;


    public BackupLoader(String folderPath){
        _folderPath = folderPath;
        _graph = GraphBuilder.newBuilder().build();

        //Testing purpose, will be replaced by a loading phase from the ressource file
        nodes = new HashMap<>();
        nodes.put(1L, 5);
    }

    public void init(){
        // Load the nodes and the total number of event received for each.
        _graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                System.out.println("Starting backup");
            }
        });
    }

    public Graph backup() throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for (Long id: nodes.keySet()){
            System.out.println("Loading node: " + id);
            es.execute(new Runnable() {
                @Override
                public void run() {
                    NodeLoader loader = new NodeLoader(id, _folderPath, nodes.get(id));
                    loader.run(_graph);
                }
            });
        }
        es.shutdown();

        es.awaitTermination(1, TimeUnit.MINUTES);

        return _graph;
    }

    public void disconnect(){
        _graph.disconnect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                System.out.println("Backup ended");
            }
        });
    }
}
