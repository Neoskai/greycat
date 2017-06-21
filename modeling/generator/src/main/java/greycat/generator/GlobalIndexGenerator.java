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

import greycat.language.Attribute;
import greycat.language.GlobalIndex;
import greycat.language.Model;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class GlobalIndexGenerator {

    static JavaSource[] generate(String packageName, Model model) {
        JavaSource[] sources = new JavaSource[model.globalIndexes().length];

        for (int i = 0; i < model.globalIndexes().length; i++) {
            sources[i] = generateGlobalIndexes(packageName, model.globalIndexes()[i]);
        }

        return sources;
    }

    private static JavaSource generateGlobalIndexes(String packageName, GlobalIndex globalIndex) {
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage(packageName);
        javaClass.setName(globalIndex.name());

        String indexName = globalIndex.name();
        String indexConstant = "INDEX_NAME";

        // index name constant
        javaClass.addField()
                .setVisibility(Visibility.PUBLIC)
                .setFinal(true)
                .setName(indexConstant)
                .setType(String.class)
                .setStringInitializer(indexName)
                .setStatic(true);

        // attribute constants
        for (Attribute att : globalIndex.attributes()) {
            javaClass.addField()
                    .setVisibility(Visibility.PUBLIC)
                    .setFinal(true)
                    .setName(att.name().toUpperCase())
                    .setType(String.class)
                    .setStringInitializer(att.name())
                    .setStatic(true);
        }

        StringBuilder indexedAttributes = new StringBuilder();
        for (Attribute att : globalIndex.attributes()) {
            indexedAttributes.append(att.name().toUpperCase());
            indexedAttributes.append(",");
        }
        indexedAttributes.deleteCharAt(indexedAttributes.length() - 1);

        // declare index
        if (globalIndex.isWithTime()) {
            MethodSource<JavaClassSource> declareIndex = javaClass.addMethod()
                    .setName("declareTimedIndex")
                    .setReturnTypeVoid()
                    .setFinal(true)
                    .setVisibility(Visibility.PUBLIC)
                    .setStatic(true);
            declareIndex.addParameter("greycat.Graph", "graph");
            declareIndex.addParameter("long", "world");
            declareIndex.addParameter("long", "time");
            declareIndex.addParameter("greycat.Callback<Boolean>", "callback");

            StringBuilder declareIndexBody = new StringBuilder();
            declareIndexBody.append("graph.declareTimedIndex(world, time, " + indexConstant + ", new greycat.Callback<greycat.NodeIndex>() {");
            declareIndexBody.append("@Override\n");
            declareIndexBody.append("public void on(greycat.NodeIndex result) {");
            declareIndexBody.append("if (callback != null) {");
            declareIndexBody.append("callback.on(true);");
            declareIndexBody.append("}");
            declareIndexBody.append("}");
            declareIndexBody.append("}, " + indexedAttributes.toString() + ");");

            declareIndex.setBody(declareIndexBody.toString());
        } else {
            MethodSource<JavaClassSource> declareIndex = javaClass.addMethod()
                    .setName("declareIndex")
                    .setReturnTypeVoid()
                    .setFinal(true)
                    .setVisibility(Visibility.PUBLIC)
                    .setStatic(true);
            declareIndex.addParameter("greycat.Graph", "graph");
            declareIndex.addParameter("long", "world");
            declareIndex.addParameter("greycat.Callback<Boolean>", "callback");

            StringBuilder declareIndexBody = new StringBuilder();
            declareIndexBody.append("graph.declareIndex(world, " + indexConstant + ", new greycat.Callback<greycat.NodeIndex>() {");
            declareIndexBody.append("@Override\n");
            declareIndexBody.append("public void on(greycat.NodeIndex result) {");
            declareIndexBody.append("if (callback != null) {");
            declareIndexBody.append("callback.on(true);");
            declareIndexBody.append("}");
            declareIndexBody.append("}");
            declareIndexBody.append("}, " + indexedAttributes.toString() + ");");

            declareIndex.setBody(declareIndexBody.toString());
        }

        // find method
        MethodSource find = javaClass.addMethod()
                .setName("find")
                .setVisibility(Visibility.PUBLIC)
                .setReturnTypeVoid()
                .setFinal(true)
                .setStatic(true);
        find.addParameter("Graph", "graph");
        find.addParameter("long", "world");
        find.addParameter("long", "time");
        find.addParameter("greycat.Callback<" + globalIndex.type()+ "[]>", "callback");

        StringBuilder paramsBuilder = new StringBuilder();
        for (Attribute att : globalIndex.attributes()) {
            find.addParameter("String", att.name());
            paramsBuilder.append(att.name());
            paramsBuilder.append(",");
        }
        paramsBuilder.deleteCharAt(paramsBuilder.length() - 1);

        StringBuilder findBody = new StringBuilder();
        findBody.append(" graph.index(world, time, "+ indexConstant + ", new Callback<greycat.NodeIndex>() {");
        findBody.append("@Override\n");
        findBody.append("public void on(greycat.NodeIndex result) {");
        findBody.append("result.findFrom(new Callback<greycat.Node[]>() {");
        findBody.append("@Override\n");
        findBody.append("public void on(greycat.Node[] result) {");
        findBody.append(globalIndex.type() + "[] typedResult = new " + globalIndex.type() + "[result.length];");
        findBody.append("java.lang.System.arraycopy(result, 0, typedResult, 0, result.length);");
        findBody.append("callback.on(typedResult);");
        findBody.append("}");
        findBody.append("}, " + paramsBuilder.toString() + ");");
        findBody.append("}");
        findBody.append("});");

        find.setBody(findBody.toString());

        // update index method
        MethodSource updateIndex = javaClass.addMethod()
                .setName("updateIndex")
                .setVisibility(Visibility.PUBLIC)
                .setReturnTypeVoid()
                .setFinal(true)
                .setStatic(true);
        updateIndex.addParameter("greycat.Graph", "graph");
        updateIndex.addParameter("long", "world");
        updateIndex.addParameter("long", "time");
        updateIndex.addParameter(globalIndex.type(), globalIndex.type().toLowerCase());
        updateIndex.addParameter("greycat.Callback<Boolean>", "callback");

        StringBuilder updateIndexBody = new StringBuilder();
        updateIndexBody.append("graph.index(world, time, " + indexConstant + ", new Callback<greycat.NodeIndex>() {");
        updateIndexBody.append("@Override\n");
        updateIndexBody.append("public void on(greycat.NodeIndex result) {");
        updateIndexBody.append("result.update(building);");
        updateIndexBody.append("if (callback != null) {");
        updateIndexBody.append("callback.on(true);");
        updateIndexBody.append("}");
        updateIndexBody.append("}");
        updateIndexBody.append("});");

        updateIndex.setBody(updateIndexBody.toString());


        // unindex
        MethodSource unindex = javaClass.addMethod()
                .setName("unindex")
                .setVisibility(Visibility.PUBLIC)
                .setReturnTypeVoid()
                .setFinal(true)
                .setStatic(true);
        unindex.addParameter("greycat.Graph", "graph");
        unindex.addParameter("long", "world");
        unindex.addParameter("long", "time");
        unindex.addParameter(globalIndex.type(), globalIndex.type().toLowerCase());
        unindex.addParameter("greycat.Callback<Boolean>", "callback");

        StringBuilder unindexBody = new StringBuilder();
        unindexBody.append("graph.index(world, time, " + indexConstant + ", new Callback<greycat.NodeIndex>() {");
        unindexBody.append("@Override\n");
        unindexBody.append("public void on(greycat.NodeIndex result) {");
        unindexBody.append("result.unindex(building);");
        unindexBody.append("if (callback != null) {");
        unindexBody.append("callback.on(true);");
        unindexBody.append("}");
        unindexBody.append("}");
        unindexBody.append("});");

        unindex.setBody(unindexBody.toString());

        return javaClass;
    }

}
