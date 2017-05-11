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
import org.junit.Test;

public class BackupLoaderTest {

    @Test
    public void test(){
        try {
            BackupLoader loader = new BackupLoader("data");
            loader.init();
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
                    System.out.println("Node found: ");
                    System.out.println(result.toString());
                }
            });
            g.lookup(0, 1200, 1, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    System.out.println("Node found: ");
                    System.out.println(result.toString());
                }
            });
            g.lookup(0, 120000, 1, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    System.out.println("Node found: ");
                    System.out.println(result.toString());
                }
            });
            g.lookup(0, 120000, 2, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    System.out.println("Node found: ");
                    System.out.println(result.toString());
                }
            });
            g.lookup(0, 120000, 3, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    System.out.println("Node found: ");
                    System.out.println(result.toString());
                }
            });
            g.lookup(0, 120000, 4, new Callback<Node>() {
                @Override
                public void on(Node result) {
                    System.out.println("Node found: ");
                    System.out.println(result.toString());
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
    }
}
