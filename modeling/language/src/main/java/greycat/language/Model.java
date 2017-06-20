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
package greycat.language;

import jdk.nashorn.internal.runtime.GlobalConstants;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Model {

    protected final Map<String, Class> classesMap;
    protected final Map<String, Constant> globalConstantsMap;
    protected final Map<String, GlobalIndex> globalIndexesMap;
    private final Map<String, CustomType> customTypesMap;

    private String name;

    public Model() {
        classesMap = new HashMap<>();
        globalConstantsMap = new HashMap<>();
        globalIndexesMap = new HashMap<>();
        customTypesMap = new HashMap<>();
    }

    public String name() {
        return this.name;
    }

    public Constant[] globalConstants() {
        return globalConstantsMap.values().toArray(new Constant[globalConstantsMap.size()]);
    }

    public GlobalIndex[] globalIndexes() {
        return globalIndexesMap.values().toArray(new GlobalIndex[globalIndexesMap.size()]);
    }

    public CustomType[] customTypes() {
        return customTypesMap.values().toArray(new CustomType[customTypesMap.size()]);
    }

    public Class[] classes() {
        return classesMap.values().toArray(new Class[classesMap.size()]);
    }

    public void parse(File content) throws Exception {
        name = content.getName();
        build(new ANTLRFileStream(content.getAbsolutePath()));
    }

    private void build(ANTLRInputStream in) {
        BufferedTokenStream tokens = new CommonTokenStream(new GreyCatModelLexer(in));
        GreyCatModelParser parser = new GreyCatModelParser(tokens);
        GreyCatModelParser.ModelDclContext modelDclCtx = parser.modelDcl();

        // constants
        for (GreyCatModelParser.ConstDclContext constDclCtx : modelDclCtx.constDcl()) {
            Constant constant = getOrAddGlobalConstant(constDclCtx);
            constant.setIsGlobal(true);
        }


        // classes
        for (GreyCatModelParser.ClassDclContext classDclCtx : modelDclCtx.classDcl()) {
            String classFqn = classDclCtx.name.getText();
            Class newClass = getOrAddClass(classFqn);

            // parents
            if (classDclCtx.parentDcl() != null) {
                final Class parentClass = getOrAddClass(classDclCtx.parentDcl().IDENT().getText());
                newClass.setParent(parentClass);
            }

            // attributes
            for (GreyCatModelParser.AttributeDclContext attDcl : classDclCtx.attributeDcl()) {
                String name = attDcl.name.getText();
                GreyCatModelParser.TypeDclContext typeDclCtx = attDcl.typeDcl();

                String type = getType(typeDclCtx);
                final Attribute attribute = new Attribute(name, type);
                newClass.addAttribute(attribute);
            }

            // relations
            for (GreyCatModelParser.RelationDclContext relDclCtx : classDclCtx.relationDcl()) {
                String name = relDclCtx.name.getText();
                String type = relDclCtx.type.getText();

                Relation relation = new Relation(name, type);
                newClass.addRelation(relation);
            }

            // references
            for (GreyCatModelParser.ReferenceDclContext refDclCtx : classDclCtx.referenceDcl()) {
                String name = refDclCtx.name.getText();
                String type = refDclCtx.type.getText();

                Reference reference = new Reference(name, type);
                newClass.addReference(reference);
            }

            // local indexes
            for (GreyCatModelParser.LocalIndexDclContext localIndexDclCtx : classDclCtx.localIndexDcl()) {
                String name = localIndexDclCtx.name.getText();
                String type = localIndexDclCtx.type.getText();

                Class indexedClass = getOrAddClass(type);

                LocalIndex localIndex = new LocalIndex(name, type);
                for (TerminalNode idxDclIdent : localIndexDclCtx.indexAttributesDcl().IDENT()) {
                    Attribute att = indexedClass.getAttribute(idxDclIdent.getText());
                    localIndex.addAttribute(att);
                }
                indexedClass.addLocalIndex(localIndex);
            }

            // constants
            for (GreyCatModelParser.ConstDclContext constDclCtx : classDclCtx.constDcl()) {
                Constant constant = getConstant(constDclCtx);
                constant.setIsGlobal(false);
                newClass.addConstant(constant);
            }
        }

        // global indexes
        for (GreyCatModelParser.GlobalIndexDclContext globalIdxDclContext : modelDclCtx.globalIndexDcl()) {
            String name = globalIdxDclContext.name.getText();
            String type = globalIdxDclContext.type.getText();

            Class indexedClass = getOrAddClass(type);

            final GlobalIndex globalIndex = getOrAddGlobalIndex(name, type);
            for (TerminalNode idxDclIdent : globalIdxDclContext.indexAttributesDcl().IDENT()) {
                Attribute att = indexedClass.getAttribute(idxDclIdent.getText());
                globalIndex.addAttribute(att);
            }
        }


        // custom types
        for (GreyCatModelParser.CustomTypeDclContext customTypeDclCtx : modelDclCtx.customTypeDcl()) {
            String customTypeName = customTypeDclCtx.name.getText();
            final CustomType newCustomType = getOrAddCustomType(customTypeName);

            // attributes
            for (GreyCatModelParser.AttributeDclContext attDcl : customTypeDclCtx.attributeDcl()) {
                if (attDcl.typeDcl().builtInTypeDcl() != null) {
                    final String value = attDcl.typeDcl().builtInTypeDcl().getText();
                    final Attribute attribute = new Attribute(attDcl.name.getText(), value);
                    newCustomType.addAttribute(attribute);

                } else if (attDcl.typeDcl().customBuiltTypeDcl() != null) {
                    final String value = attDcl.typeDcl().customBuiltTypeDcl().getText();
                    final Attribute attribute = new Attribute(attDcl.name.getText(), value);
                    newCustomType.addAttribute(attribute);
                }
            }

            // constants
            for (GreyCatModelParser.ConstDclContext constDclCtx : customTypeDclCtx.constDcl()) {
                Constant constant = getConstant(constDclCtx);
                constant.setIsGlobal(false);
                newCustomType.addConstant(constant);
            }
        }

    }


    private String getType(GreyCatModelParser.TypeDclContext typeDclContext) {
        String type = null;
        if (typeDclContext.builtInTypeDcl() != null) {
            type = typeDclContext.builtInTypeDcl().getText();
        } else if (typeDclContext.customBuiltTypeDcl() != null) {
            typeDclContext.customBuiltTypeDcl().getText();
        }

        return type;
    }

    private Constant getConstant(GreyCatModelParser.ConstDclContext constDclCtx) {
        String name = constDclCtx.name.getText();
        String type = getType(constDclCtx.typeDcl());
        String value = constDclCtx.value != null ? constDclCtx.value.getText() : null;

        return new Constant(name, type, value);
    }


    private Class getOrAddClass(String fqn) {
        Class c = classesMap.get(fqn);
        if (c == null) {
            c = new Class(fqn);
            classesMap.put(fqn, c);
        }
        return c;
    }

    private Constant getOrAddGlobalConstant(GreyCatModelParser.ConstDclContext constDclCtx) {
        String name = constDclCtx.name.getText();

        Constant c = globalConstantsMap.get(name);
        if (c == null) {
            String type = getType(constDclCtx.typeDcl());
            String value = constDclCtx.value != null ? constDclCtx.value.getText() : null;
            c = new Constant(name, type, value);
            globalConstantsMap.put(name, c);
        }
        return c;
    }

    private GlobalIndex getOrAddGlobalIndex(String name, String type) {
        GlobalIndex gi = globalIndexesMap.get(name);
        if (gi == null) {
            gi = new GlobalIndex(name, type);
            globalIndexesMap.put(name, gi);
        }
        return gi;
    }

    private CustomType getOrAddCustomType(String name) {
        CustomType ct = customTypesMap.get(name);
        if (ct == null) {
            ct = new CustomType(name);
            customTypesMap.put(name, ct);
        }
        return ct;
    }

}
