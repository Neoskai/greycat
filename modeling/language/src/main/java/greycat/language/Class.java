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


import java.util.*;

public class Class extends ASTNode {
    private final String name;
    private final Map<String, Attribute> attributes;
    private final Map<String, Relation> relations;
    private final Map<String, Reference> references;
    private final Map<String, LocalIndex> localIndexes;
    private final Map<String, Constant> constants;

    private Class parent;

    public Class(String name) {
        this.name = name;
        this.attributes = new HashMap<>();
        this.relations = new HashMap<>();
        this.references = new HashMap<>();
        this.localIndexes = new HashMap<>();
        this.constants = new HashMap<>();
    }


    public Collection<Constant> constants() {
        return this.constants.values();
    }

    public void addConstant(Constant constant) {
        this.constants.put(constant.name(), constant);
    }

    public Collection<LocalIndex> localIndexes() {
        return this.localIndexes.values();
    }

    public void addLocalIndex(LocalIndex localIndex) {
        localIndexes.put(localIndex.name(), localIndex);
    }

    public Collection<Attribute> attributes() {
        return attributes.values();
    }

    public Attribute getAttribute(String name) {
        for (Attribute att : attributes()) {
            if (att.name().equals(name)) {
                return att;
            }
        }
        return null;
    }

    public void addAttribute(Attribute att) {
        attributes.put(att.name(), att);
    }

    public Collection<Relation> relations() {
        return relations.values();
    }

    public Relation getRelation(String name) {
        for (Relation rel : relations()) {
            if (rel.name().equals(name)) {
                return rel;
            }
        }
        return null;
    }

    public void addRelation(Relation rel) {
        relations.put(rel.name(), rel);
    }

    public Collection<Reference> references() {
        return references.values();
    }

    public Reference getReference(String name) {
        for (Reference ref : references()) {
            if (ref.name().equals(name)) {
                return ref;
            }
        }
        return null;
    }

    public void addReference(Reference ref) {
        references.put(ref.name(), ref);
    }


    public Class parent() {
        return parent;
    }

    public void setParent(Class parent) {
        this.parent = parent;
    }

    public String name() {
        return name;
    }
}
