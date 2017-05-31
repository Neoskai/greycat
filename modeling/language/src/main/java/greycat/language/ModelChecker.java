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

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;


public class ModelChecker {


    @SuppressWarnings("Duplicates")
    public void check(File content) throws Exception {
        BufferedTokenStream tokens = new CommonTokenStream(new GreyCatModelLexer(new ANTLRFileStream(content.getAbsolutePath())));
        GreyCatModelParser parser = new GreyCatModelParser(tokens);
        GreyCatModelParser.ModelDclContext modelDclCtx = parser.modelDcl();

        // classes
        for (GreyCatModelParser.ClassDclContext classDclCxt : modelDclCtx.classDcl()) {
            String classFqn = null;
            if (classDclCxt.TYPE_NAME() != null) {
                classFqn = classDclCxt.TYPE_NAME().toString();
            }
            if (classDclCxt.IDENT() != null) {
                classFqn = classDclCxt.IDENT().toString();
            }

            // parents
            if (classDclCxt.parentDcl() != null) {
                String parentClassFqn = null;
                if (classDclCxt.parentDcl().TYPE_NAME() != null) {
                    parentClassFqn = classDclCxt.parentDcl().TYPE_NAME().toString();
                }
                if (classDclCxt.parentDcl().IDENT() != null) {
                    parentClassFqn = classDclCxt.parentDcl().IDENT().toString();
                }
                if (!isClassifierDeclared(parentClassFqn, modelDclCtx)) {
                    throw new RuntimeException(parentClassFqn + " is specified as parent of " + classFqn + " but is not declared");
                }
            }

            // attributes (nothing to check)


            // relations
            for (GreyCatModelParser.RelationDclContext relDecCxt : classDclCxt.relationDcl()) {
                String relName, relType;
                boolean isToOne = relDecCxt.toOneDcl() != null;
                if (isToOne) {
                    // toOne
                    relName = relDecCxt.toOneDcl().IDENT().get(0).getText();
                    if (relDecCxt.toOneDcl().TYPE_NAME() == null) {
                        relType = relDecCxt.toOneDcl().IDENT(1).toString();
                    } else {
                        relType = relDecCxt.toOneDcl().TYPE_NAME().toString();
                    }
                } else {
                    // toMany
                    relName = relDecCxt.toManyDcl().IDENT().get(0).getText();
                    if (relDecCxt.toManyDcl().TYPE_NAME() == null) {
                        relType = relDecCxt.toManyDcl().IDENT(1).toString();
                    } else {
                        relType = relDecCxt.toManyDcl().TYPE_NAME().toString();
                    }

                }
                if (!isClassifierDeclared(relType, modelDclCtx)) {
                    throw new RuntimeException(relType + " is specified as relation of " + classFqn + " but is not declared");
                }

                if (!isToOne) {
                    // relation indexes
                    if (relDecCxt.toManyDcl().relationIndexDcl() != null) {
                        for (TerminalNode relationIdxIdent : relDecCxt.toManyDcl().relationIndexDcl().IDENT()) {
                            String relIndexedAttName = relationIdxIdent.getText();
                            if (!isAttOfCassifier(relType, relIndexedAttName, modelDclCtx)) {
                                throw new RuntimeException(relIndexedAttName + " is specified as index of relation " + relType + " but is not an attribute of " + relType);
                            }
                        }
                    }
                }
            }

            // global indexes
            for (int i = 0; i < classDclCxt.indexDcl().size(); i++) {
                GreyCatModelParser.IndexDclContext indexDclCxt = classDclCxt.indexDcl().get(i);
                for (TerminalNode idxDclIdent : indexDclCxt.IDENT()) {
                    String indexedAttName = idxDclIdent.getText();

                    if (!isAttOfCassifier(classFqn, indexedAttName, modelDclCtx)) {
                        throw new RuntimeException(indexedAttName + " is specified as index of " + classFqn + " but is not an attribute of " + classFqn);
                    }

                }

            }
        }
    }

    private boolean isAttOfCassifier(String classifier, String att, GreyCatModelParser.ModelDclContext modelDclCtx) {
        for (GreyCatModelParser.ClassDclContext classDclCxt : modelDclCtx.classDcl()) {
            String classFqn = null;
            if (classDclCxt.TYPE_NAME() != null) {
                classFqn = classDclCxt.TYPE_NAME().toString();
            }
            if (classDclCxt.IDENT() != null) {
                classFqn = classDclCxt.IDENT().toString();
            }
            if (classFqn.equals(classifier)) {
                for (GreyCatModelParser.AttributeDclContext attDcl : classDclCxt.attributeDcl()) {
                    String attName = attDcl.IDENT().getText();
                    if (attName.equals(att)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean isClassifierDeclared(String fqn, GreyCatModelParser.ModelDclContext modelDclCtx) {

        // classes
        for (GreyCatModelParser.ClassDclContext classDclCxt : modelDclCtx.classDcl()) {
            String classFqn = null;
            if (classDclCxt.TYPE_NAME() != null) {
                classFqn = classDclCxt.TYPE_NAME().toString();
            }
            if (classDclCxt.IDENT() != null) {
                classFqn = classDclCxt.IDENT().toString();
            }
            if (classFqn.equals(fqn)) {
                return true;
            }

        }
        // enums
        for (GreyCatModelParser.EnumDclContext enumDclCtx : modelDclCtx.enumDcl()) {
            String enumFqn = null;
            if (enumDclCtx.TYPE_NAME() != null) {
                enumFqn = enumDclCtx.TYPE_NAME().toString();
            }
            if (enumDclCtx.IDENT() != null) {
                enumFqn = enumDclCtx.IDENT().toString();
            }
            if (enumFqn.equals(fqn)) {
                return true;
            }
        }
        return false;

    }

}