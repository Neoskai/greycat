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

package greycat.aerospike;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

import org.junit.Test;

import java.io.IOException;


public class StorageTest {

    /*@Test
    public void test() throws IOException{
        AerospikeClient client = new AerospikeClient("localhost", 3000);

        // First = Namespace (test) / Second = Set (demo) / Third = Key (putgetkey)
        Key key = new Key("test", "demo", "putgetkey");
        Bin bin1 = new Bin("name", "Loic");
        Bin bin2 = new Bin("location", "Here");

        // Write a record
        // First = Policy (null) / Second = key / rest = datas associated to this key
        client.put(null, key, bin1, bin2);

        // Read a record
        Record record = client.get(null, key);

        System.out.println("Record found: " + record);

        client.close();
    }*/
}
