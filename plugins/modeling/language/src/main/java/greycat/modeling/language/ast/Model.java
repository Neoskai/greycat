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
package greycat.modeling.language.ast;

import greycat.modeling.language.GreyCatModelLexer;
import greycat.modeling.language.GreyCatModelParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Model {

    private final Map<String, Classifier> classifiers;

    public Model() {
        classifiers = new HashMap<>();
    }

    public Classifier[] classifiers() {
        return classifiers.values().toArray(new Classifier[classifiers.size()]);
    }

    public void addClassifier(Classifier classifier) {
        classifiers.put(classifier.name(), classifier);
    }

    public Classifier get(String fqn) {
        return classifiers.get(fqn);
    }


    public static Model parse(File content, Model model) throws Exception {
        return build(new ANTLRFileStream(content.getAbsolutePath()), model);
    }

    private static Model build(ANTLRInputStream in, Model model) {
        BufferedTokenStream tokens = new CommonTokenStream(new GreyCatModelLexer(in));
        GreyCatModelParser parser = new GreyCatModelParser(tokens);
        GreyCatModelParser.ModelDclContext modelDclCtx = parser.modelDcl();

        // enums
        for (GreyCatModelParser.EnumDclContext enumDclCtx : modelDclCtx.enumDcl()) {
            String fqn = null;
            if (enumDclCtx.TYPE_NAME() != null) {
                fqn = enumDclCtx.TYPE_NAME().toString();
            }
            if (enumDclCtx.IDENT() != null) {
                fqn = enumDclCtx.IDENT().toString();
            }
            final Enum enumClass = (Enum) getOrAddEnum(model, fqn);
            for (TerminalNode literal : enumDclCtx.enumLiteralsDcl().IDENT()) {
                enumClass.addLiteral(literal.getText());
            }
        }

        // classes
        for (GreyCatModelParser.ClassDclContext classDclCxt : modelDclCtx.classDcl()) {
            String classFqn = null;
            if (classDclCxt.TYPE_NAME() != null) {
                classFqn = classDclCxt.TYPE_NAME().toString();
            }
            if (classDclCxt.IDENT() != null) {
                classFqn = classDclCxt.IDENT().toString();
            }
            final Class newClass = (Class) getOrAddClass(model, classFqn);

            // parents
            if (classDclCxt.parentDcl() != null) {
                if (classDclCxt.parentDcl().TYPE_NAME() != null) {
                    final Class newClassTT = (Class) getOrAddClass(model, classDclCxt.parentDcl().TYPE_NAME().toString());
                    newClass.setParent(newClassTT);
                }
                if (classDclCxt.parentDcl().IDENT() != null) {
                    final Class newClassTT = (Class) getOrAddClass(model, classDclCxt.parentDcl().IDENT().toString());
                    newClass.setParent(newClassTT);
                }
            }

            // attributes
            for (GreyCatModelParser.AttributeDclContext attDcl : classDclCxt.attributeDcl()) {
                String name = attDcl.IDENT().getText();
                GreyCatModelParser.AttributeTypeDclContext attTypeDclCxt = attDcl.attributeTypeDcl();
                String value = attTypeDclCxt.getText();
                boolean isArray = false;
                if (value.endsWith("[]")) {
                    value = value.substring(0, value.length() - 2);
                    isArray = true;
                }

                final Attribute attribute = new Attribute(name, value, isArray);
                newClass.addProperty(attribute);
            }

            // relations
            for (GreyCatModelParser.RelationDclContext relDecCxt : classDclCxt.relationDcl()) {
                String name = relDecCxt.IDENT().get(0).getText();
                String type;
                if (relDecCxt.TYPE_NAME() == null) {
                    type = relDecCxt.IDENT(1).toString();
                } else {
                    type = relDecCxt.TYPE_NAME().toString();
                }
                final Relation relation = new Relation(name, type);

                // relation indexes
                if (relDecCxt.relationIndexDcl() != null) {
                    for (TerminalNode relationIdxIdent : relDecCxt.relationIndexDcl().IDENT()) {
                        relation.addIndexedAttribute(relationIdxIdent.getText());
                    }
                }

                newClass.addProperty(relation);
            }

            // global indexes
            for (int i = 0; i < classDclCxt.indexDcl().size(); i++) {
                GreyCatModelParser.IndexDclContext indexDclCxt = classDclCxt.indexDcl().get(i);
                String idxName = newClass.name() + "Idx" + i;
                if (indexDclCxt.indexNameDcl() != null) {
                    idxName = indexDclCxt.indexNameDcl().getText();
                }
                Index idx = new Index(idxName);
                for (TerminalNode idxDclIdent : indexDclCxt.IDENT()) {
                    Property indexedProperty = newClass.getProperty(idxDclIdent.getText());
                    if (!(indexedProperty instanceof Attribute)) {
                        throw new RuntimeException("index declaration invalid: " + idxDclIdent.getText() + " is not a valid attribute");
                    }
                    Attribute att = (Attribute) indexedProperty;
                    idx.addAttribute(att);
                }
                if (indexDclCxt.withTimeDcl() != null) {
                    idx.setWithTime(true);
                }
                newClass.addIndex(idx);

            }
        }


        return model;
    }


    private static Classifier getOrAddClass(Model model, String fqn) {
        Classifier previous = model.get(fqn);
        if (previous != null) {
            return previous;
        }
        previous = new Class(fqn);
        model.addClassifier(previous);
        return previous;
    }

    private static Classifier getOrAddEnum(Model model, String fqn) {
        Classifier previous = model.get(fqn);
        if (previous != null) {
            return previous;
        }
        previous = new Enum(fqn);
        model.addClassifier(previous);
        return previous;
    }

}
