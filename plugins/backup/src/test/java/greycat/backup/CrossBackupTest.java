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
import greycat.Node;
import greycat.rocksdb.RocksDBStorage;
import greycat.scheduler.NoopScheduler;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class CrossBackupTest {

    @Test
    public void backupTest(){
        String rocksPath = getClass().getClassLoader().getResource("data").getFile();
        String sparkeyPath = rocksPath+ "/logs";


        Graph graph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage(rocksPath))
                .build();

        CrossBackup.loadBackup(graph, sparkeyPath);

        graph.connect(null);

        graph.lookup(0, 1000, 201, new Callback<Node>() {
            @Override
            public void on(Node result) {
                assertNotEquals(result,null);
            }
        });

        graph.lookup(0, 1000, 401, new Callback<Node>() {
            @Override
            public void on(Node result) {
                assertNotEquals(result,null);
            }
        });

        graph.disconnect(null);
    }
}
