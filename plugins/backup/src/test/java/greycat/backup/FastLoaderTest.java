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
import greycat.Node;
import greycat.backup.fastloader.FastBackupLoader;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FastLoaderTest {

    @Test
    public void totalBackup() throws IOException {
        try {
            long initialBench = System.currentTimeMillis();
            FastBackupLoader loader = new FastBackupLoader("data");
            Graph g = loader.backup();

            System.out.println("Backup took: " + ((System.currentTimeMillis()-initialBench)/1000) + " s");

            g.connect(new Callback<Boolean>() {
                @Override
                public void on(Boolean result) {
                    System.out.println("Connecting to backup graph");
                }
            });
            g.lookup(0, 1001, 1, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    assertTrue(result != null);
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
