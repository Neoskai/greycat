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

import greycat.Callback;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Node;
import greycat.backup.loader.BackupLoader;

import java.io.File;
import java.io.IOException;

public class BackupSample {
    public static void main(String[] args) {
        try {
            totalBackup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void totalBackup() throws IOException {
        try {
            BackupLoader loader = new BackupLoader("data");
            Graph g = loader.backup();

            g.connect(new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    System.out.println("Connecting to backup graph");
                }
            });
            g.lookup(0, 1001, 1, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    System.out.println(result);
                }
            });
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

    public static void nodeBackup() throws IOException {
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

    public static void backupSequence() throws IOException {
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

    public static void testNodeSequence() throws IOException {
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
