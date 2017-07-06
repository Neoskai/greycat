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

package greycatTest.utility;

import greycat.Type;
import greycat.utility.JsonBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @ignore ts
 */
public class JsonBuilderTest {
    @Test
    public void testBuilder(){

        String boolJson = JsonBuilder.buildObject(Type.BOOL, true);
        String boolWriting = "{\"_type\":1, \"_value\":true}";
        assertEquals(boolJson, boolWriting);

        String stringJson = JsonBuilder.buildObject(Type.STRING, "hello");
        String stringWriting = "{\"_type\":2, \"_value\":\"hello\"}";
        assertEquals(stringJson, stringWriting);

        String longJson =JsonBuilder.buildObject(Type.LONG, 1712771606L);
        String longWriting = "{\"_type\":3, \"_value\":1712771606}";
        assertEquals(longJson, longWriting);

        String intJson =JsonBuilder.buildObject(Type.INT, -70308288);
        String intWriting = "{\"_type\":4, \"_value\":-70308288}";
        assertEquals(intJson, intWriting);

        String doubleJson =JsonBuilder.buildObject(Type.DOUBLE, 3973226699.47893);
        String doubleWriting = "{\"_type\":5, \"_value\":3.97322669947893E9}";
        assertEquals(doubleJson, doubleWriting);
    }
}
