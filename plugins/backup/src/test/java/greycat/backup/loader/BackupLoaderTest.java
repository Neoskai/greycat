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
package greycat.backup.loader;

import greycat.Callback;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.loader.BackupLoader;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Care when using lookup, node IDS are changing after each backup, so lookup result might not be as expected
 */
public class BackupLoaderTest {

    @Ignore
    @Test
    public void totalBackup() throws IOException {
        try {
            BackupLoader loader = new BackupLoader("data");
            Graph g = loader.backup();

            g.connect(new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    System.out.println("Connecting to backup graph");
                }
            });
            /*g.lookup(0, 1001, 1, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });
            g.lookup(0, 1200, 1L, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });
            g.lookup(0, 120000, 2, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });
            g.lookup(0, 120000, 3, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });
            g.lookup(0, 120000, 4, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });
            g.lookup(0, 120000, 5, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });*/
            g.disconnect(new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    System.out.println("Disconnecting from backup graph");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

        File data = new File("data/data");
        if (data.exists()) {
            delete(data);
        }
    }

    @Ignore
    @Test
    public void nodeBackup() throws IOException {
        try {
            Graph newGraph =  new GraphBuilder()
                    //.withStorage(new RocksDBStorage("data"))
                    .withMemorySize(100000)
                    .build();

            BackupLoader loader = new BackupLoader("data", newGraph);
            Graph g = loader.nodeBackup(1L);
            g.connect(null);
            /*g.lookup(0, 1000, 1L, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });

            g.lookup(0, 1020, 0, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(null, result);
                }
            });

            g.lookup(0, 1030, 2, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(null, result);
                }
            });*/

            g.disconnect(null);
        } catch (Exception e){
            e.printStackTrace();
        }

        File data = new File("data/data");
        if (data.exists()) {
            delete(data);
        }
    }

    @Ignore
    @Test
    public void backupSequence() throws IOException {
        try {
            Graph newGraph = new GraphBuilder()
                    //.withStorage(new RocksDBStorage("data"))
                    .withMemorySize(100000)
                    .build();
            newGraph.connect(null);

            BackupLoader loader = new BackupLoader("data", newGraph);
            Graph g = loader.backupSequence(1494945502076L, System.currentTimeMillis());
            g.connect(null);
            /*g.lookup(0, 500000, 1L, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(null, result);
                }
            });

            g.lookup(0, 1000, 0, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(result, null);
                }
            });

            g.lookup(0, 1000, 2, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(result, null);
                }
            });

            g.lookup(0, 975000, 5, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });*/

            g.disconnect(null);
        } catch (Exception e){
            e.printStackTrace();
        }

        File data = new File("data/data");
        if (data.exists()) {
            delete(data);
        }
    }

    @Ignore
    @Test
    public void testNodeSequence() throws IOException {
        try {
            Graph newGraph = new GraphBuilder()
                    //.withStorage(new RocksDBStorage("data"))
                    .withMemorySize(100000)
                    .build();

            BackupLoader loader = new BackupLoader("data", newGraph);
            Graph g = loader.backupNodeSequence(1494945502076L, System.currentTimeMillis(), 5L);
            g.connect(null);
            /*g.lookup(0, 500000, 1L, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(null, result);
                }
            });

            g.lookup(0, 1000, 0, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(result, null);
                }
            });

            g.lookup(0, 1000, 2, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertEquals(result, null);
                }
            });

            g.lookup(0, 975000, 5, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
                }
            });*/

            g.disconnect(null);
        } catch (Exception e){
            e.printStackTrace();
        }

        File data = new File("data/data");
        if (data.exists()) {
            delete(data);
        }
    }

    private static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
            } else {
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }

        } else {
            //if file, then delete it
            file.delete();
        }
    }

}
