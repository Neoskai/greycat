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

import greycat.Type;
import greycat.language.Attribute;
import greycat.language.Constant;
import greycat.language.CustomType;
import greycat.language.Model;
import greycat.utility.HashHelper;
import greycat.utility.MetaConst;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CustomTypeGenerator {

    static JavaSource[] generate(String packageName, Model model) {
        JavaSource[] sources = new JavaSource[model.classes().length];

        for (int i = 0; i < model.customTypes().length; i++) {
            sources[i] = generateCustomType(packageName, model.customTypes()[i]);
        }

        return sources;
    }

    private static JavaClassSource generateCustomType(String packageName, CustomType customType) {
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage(packageName);
        javaClass.setName(customType.name());
        javaClass.addImport(Type.class);

        if (customType.parent() != null) {
            javaClass.setSuperType(packageName + "." + customType.parent().name());
        } else {
            javaClass.setSuperType("greycat.base.BaseCustomTypeSingle");
        }

        StringBuilder TS_GET_SET = new StringBuilder();
        customType.properties().forEach(o -> {
            if (o instanceof Attribute) {
                Attribute attribute = (Attribute) o;
                if (TypeManager.isPrimitive(attribute.type())) {
                    TS_GET_SET.append("get " + attribute.name() + "() : " + TypeManager.classTsName(attribute.type()) + " {return this.get" + Generator.upperCaseFirstChar(attribute.name()) + "();}\n");
                    TS_GET_SET.append("set " + attribute.name() + "(p : " + TypeManager.classTsName(attribute.type()) + "){ this.set" + Generator.upperCaseFirstChar(attribute.name()) + "(p);}\n");
                }
            }
        });
        //generate TS getter and setter
        javaClass.getJavaDoc().setFullText("<pre>{@extend ts\n" + TS_GET_SET + "\n}\n</pre>");

        // field for meta
        javaClass.addField()
                .setVisibility(Visibility.PUBLIC)
                .setFinal(true)
                .setName("META")
                .setType(greycat.utility.Meta.class)
                .setLiteralInitializer("new greycat.utility.Meta(" + "\"" + customType.name() + "\"" + ","
                        + HashHelper.hash(customType.name()) + ","
                        + HashHelper.hash(customType.name()) + ");")
                .setStatic(true);


        // init method
        MethodSource<JavaClassSource> init = javaClass.addMethod()
                .setName("init")
                .setVisibility(Visibility.PUBLIC)
                .setReturnTypeVoid();
        StringBuilder initBodyBuilder = new StringBuilder();

        // override typeAt
        MethodSource<JavaClassSource> typeAt = javaClass.addMethod()
                .setName("typeAt")
                .setVisibility(Visibility.PUBLIC)
                .setReturnType(int.class);
        typeAt.addParameter("int", "index");
        StringBuilder typeAtBodyBuilder = new StringBuilder();

        // override getAt
        MethodSource<JavaClassSource> getAt = javaClass.addMethod()
                .setName("getAt")
                .setVisibility(Visibility.PUBLIC)
                .setReturnType(Object.class);
        getAt.addParameter("int", "index");
        StringBuilder getAtBodyBuilder = new StringBuilder();

        // override setAt
        MethodSource<JavaClassSource> setAt = javaClass.addMethod()
                .setName("setAt")
                .setVisibility(Visibility.PUBLIC)
                .setReturnType(greycat.Container.class);
        setAt.addParameter("int", "index");
        setAt.addParameter("int", "type");
        setAt.addParameter("Object", "value");
        StringBuilder setAtBodyBuilder = new StringBuilder();

        customType.properties().forEach(o -> {
            // constants
            if (o instanceof Constant) {
                Constant constant = (Constant) o;
                String value = constant.value();

                if (constant.type().equals("Task")) {
                    typeAtBodyBuilder.append("if (index == ").append(constant.name().toUpperCase()).append(".hash) {").append("return Type.TASK;").append("}");
                    getAtBodyBuilder.append("if (index == ").append(constant.name().toUpperCase()).append(".hash) {").append("return " + constant.name().toUpperCase() + ".value" + ";").append("}");
                    setAtBodyBuilder.append("if (type == Type.TASK && index == ").append(constant.name().toUpperCase() + ".hash)").append("{")
                            .append(constant.name().toUpperCase() + ".value ").append("= (greycat.Task) value;").append("return this;").append("}");

                    if (value != null) {
                        value = "greycat.Tasks.newTask().parse(\"" + value.replaceAll("\"", "'").trim() + "\",null)";
                    }
                } else if (!constant.type().equals("String") && value != null) {
                    value = value.replaceAll("\"", "");
                }

                // field for meta
                javaClass.addField()
                        .setVisibility(Visibility.PUBLIC)
                        .setFinal(true)
                        .setName(constant.name().toUpperCase())
                        .setType(MetaConst.class)
                        .setLiteralInitializer("new greycat.utility.MetaConst(\"" + constant.name() + "\", "
                                + TypeManager.typeName(constant.type()) + ", "
                                + HashHelper.hash(constant.name()) + ", " + value + ");")
                        .setStatic(true);

            } else if (o instanceof Attribute) {
                Attribute att = (Attribute) o;
                // field for meta
                javaClass.addField()
                        .setVisibility(Visibility.PUBLIC)
                        .setFinal(true)
                        .setName(att.name().toUpperCase())
                        .setType(greycat.utility.Meta.class)
                        .setLiteralInitializer("new greycat.utility.Meta(\"" + att.name() + "\", "
                                + TypeManager.typeName(att.type()) + ", "
                                + HashHelper.hash(att.name()) + ");")
                        .setStatic(true);

                // getter
                javaClass.addMethod()
                        .setName("get" + Generator.upperCaseFirstChar(att.name()))
                        .setVisibility(Visibility.PUBLIC)
                        .setFinal(true)
                        .setReturnType(TypeManager.className(att.type()))
                        .setBody("return (" + TypeManager.className(att.type()) + ") getAt(" + att.name().toUpperCase() + ".hash);");

                // setter
                javaClass.addMethod()
                        .setName("set" + Generator.upperCaseFirstChar(att.name()))
                        .setVisibility(Visibility.PUBLIC)
                        .setFinal(true)
                        .setReturnType(customType.name())
                        .setBody("setAt(" + att.name().toUpperCase() + ".hash ," +
                                "greycat." + TypeManager.typeName(att.type()) + "," + att.name() + ");\nreturn this;")
                        .addParameter(TypeManager.className(att.type()), att.name());

                // init
                if (att.value() != null) {
                    initBodyBuilder.append(DefaultValueGenerator.createMethodBody(att).toString());
                }

            }
        });


        // constructor
        MethodSource<JavaClassSource> constructor = javaClass.addMethod().setConstructor(true);
        constructor.addParameter("greycat.struct.EStructArray", "e");
        StringBuilder constructorBody = new StringBuilder();
        constructorBody.append("super(e);");
        constructor.setBody(constructorBody.toString());

        // toString
        MethodSource toString = javaClass.addMethod()
                .setName("toString")
                .setPublic()
                .setReturnType(String.class);
        final StringBuilder toStringBody = new StringBuilder();
        toStringBody.append("return \"" + customType.name() + "(\"");
        customType.properties().forEach(o -> {
            if (o instanceof Attribute) {
                Attribute att = (Attribute) o;
                toStringBody.append(" + " + "\"" + att.name() + ": \" " + "+ get" + Generator.upperCaseFirstChar(att.name()) + "()");
                toStringBody.append("+ \",\"");
            }
        });
        String finalToString = "";
        if (toStringBody.length() > 0) {
            finalToString = toStringBody.toString().substring(0, toStringBody.length() - 2);
        }
        finalToString += ")\";";
        toString.setBody(finalToString);

        init.setBody(initBodyBuilder.toString());

        typeAtBodyBuilder.append("return super.typeAt(index);");
        typeAt.setBody(typeAtBodyBuilder.toString());

        getAtBodyBuilder.append("return super.getAt(index);");
        getAt.setBody(getAtBodyBuilder.toString());

        setAtBodyBuilder.append("return super.setAt(index, type, value);");
        setAt.setBody(setAtBodyBuilder.toString());

        return javaClass;
    }

}
