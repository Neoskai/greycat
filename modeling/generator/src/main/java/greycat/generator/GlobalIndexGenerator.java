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
package greycat.generator;

import greycat.language.GlobalIndex;
import greycat.language.Model;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaSource;

public class GlobalIndexGenerator {

    static JavaSource[] generate(String packageName, Model model) {
        JavaSource[] sources = new JavaSource[model.globalIndexes().length];

        for (int i = 0; i < model.customTypes().length; i++) {
            sources[i] = generateGlobalIndexes(packageName, model.globalIndexes()[i]);
        }

        return sources;
    }

    private static JavaSource generateGlobalIndexes(String packageName, GlobalIndex globalIndex) {
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage(packageName);
        javaClass.setName(globalIndex.name());


        return javaClass;
    }

}
