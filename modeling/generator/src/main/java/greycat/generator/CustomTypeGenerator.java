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
import greycat.language.Constant;
import greycat.language.CustomType;
import greycat.language.Model;
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

        javaClass.setSuperType("greycat.base.BaseCustomTypeSingle");

        // constants
        for (Constant constant : customType.constants()) {
            String value = constant.value();
            if (!constant.type().equals("String")) {
                value = value.replaceAll("\"", "");
            }
            javaClass.addField()
                    .setVisibility(Visibility.PUBLIC)
                    .setFinal(true)
                    .setName(constant.name())
                    // TODO custom type
                    .setType(TypeManager.builtInClassName(constant.type()))
                    .setLiteralInitializer(value)
                    .setStatic(true);
        }

        for (Attribute att : customType.attributes()) {
            // field attribute name
            javaClass.addField()
                    .setVisibility(Visibility.PUBLIC)
                    .setFinal(true)
                    .setName(att.name().toUpperCase())
                    .setType(String.class)
                    .setStringInitializer(att.name())
                    .setStatic(true);

            // field attribute hash
            javaClass.addField()
                    .setVisibility(Visibility.PUBLIC)
                    .setFinal(true)
                    .setName(att.name().toUpperCase() + "_H")
                    .setType(int.class)
                    .setLiteralInitializer("greycat.utility.HashHelper.hash(" + att.name().toUpperCase() + ")")
                    .setStatic(true);

            // getter
            javaClass.addMethod()
                    .setName("get" + Generator.upperCaseFirstChar(att.name()))
                    .setVisibility(Visibility.PUBLIC)
                    .setFinal(true)
                    // TODO check for custom types
                    .setReturnType(TypeManager.builtInClassName(att.type()))
                    .setBody("return (" + TypeManager.builtInClassName(att.type()) + ") getAt(" + att.name().toUpperCase() + "_H" + ");");

            // setter
            javaClass.addMethod()
                    .setName("set" + Generator.upperCaseFirstChar(att.name()))
                    .setVisibility(Visibility.PUBLIC)
                    .setFinal(true)
                    .setReturnTypeVoid()
                    // TODO check custom types
                    .setBody("this._backend.node(DEF_NODE).setAt(" + att.name().toUpperCase() + "_H," +
                            "greycat." + TypeManager.builtInTypeName(att.type()) + "," + att.name() + ");")
                    // TODO check for custom types
                    .addParameter(TypeManager.builtInClassName(att.type()), att.name());

        }

        // constructor
        MethodSource<JavaClassSource> constructor = javaClass.addMethod().setConstructor(true);
        constructor.addParameter("greycat.struct.EGraph", "e");
        StringBuilder constructorBody = new StringBuilder();
        constructorBody.append("super(e);");
        constructor.setBody(constructorBody.toString());

        // toString
        MethodSource toString = javaClass.addMethod()
                .setName("toString")
                .setPublic()
                .setReturnType(String.class);
        String toStringBody = "return \"" + customType.name() + "(\"";
        for (Attribute att : customType.attributes()) {
            toStringBody += (" + " + "\"" + att.name() + ": \" " + "+ get" + Generator.upperCaseFirstChar(att.name()) + "()");
            toStringBody += "+ \",\"";
        }
        if (customType.attributes().size() > 0) {
            toStringBody = toStringBody.substring(0, toStringBody.length() - 2);
        }
        toStringBody += ")\";";
        toString.setBody(toStringBody);

        return javaClass;
    }

}
