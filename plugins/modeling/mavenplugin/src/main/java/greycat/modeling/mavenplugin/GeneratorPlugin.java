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
package greycat.modeling.mavenplugin;

import greycat.modeling.generator.Generator;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class GeneratorPlugin extends AbstractMojo {

    /**
     * File containing the data structure, or the directory to navigate to get file(s).
     * If there is several TYG files in the folder, they will be merged in one.
     *
     * File(s) should have the "{@value Generator#FILE_EXTENSION}".
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/resources")
    private File srcFiles;

    /**
     * Define if a deep scan is made or not, i.e., if we navigate only in the first level or if we navigate all the folder tree
     */
    @Parameter(defaultValue = "false", alias = "deepScan")
    private boolean doDeepScan;

    /**
     * GreyCat Plugin name
     */
    @Parameter(defaultValue = "ModelingPlugin")
    private String pluginName;

    /**
     * Root package in which the Java classes are generated.
     * They are translate into TypeScript namespace.
     *
     * TIP: keep it as simple as possible, one level should be enough
     */
    @Parameter(defaultValue = "modeling")
    private String packageName;

    /**
     * Folder in which the files are generated
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/modeling")
    private File targetGen;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    /**
     * Define with the Java classes are generated are not
     */
    @Parameter(defaultValue = "true")
    private boolean generateJava;

    @Parameter(defaultValue = "false")
    private boolean generateJS;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Generator generator = new Generator();
        try {
            if(doDeepScan) {
                generator.deepScan(srcFiles);
            } else {
                generator.scan(srcFiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException("Error during file scanning",e);
        }

        String gcVersion = "";
        for(int i=0;i<project.getDependencies().size();i++) {
                Dependency dependency = project.getDependencies().get(i);
                if(dependency.getGroupId().equals("com.datathings") && dependency.getArtifactId().equals("greycat")) {
                    gcVersion = dependency.getVersion();
                    break;
                }
            }

        generator.mvnGenerate(packageName, pluginName,targetGen,generateJava,generateJS,gcVersion,project);
        project.addCompileSourceRoot(targetGen.getAbsolutePath());
    }
}
