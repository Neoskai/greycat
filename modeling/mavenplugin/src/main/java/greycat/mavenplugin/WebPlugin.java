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
package greycat.mavenplugin;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.kevoree.resolver.MavenResolver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;

@Mojo(name = "web", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class WebPlugin extends AbstractMojo {

    @Parameter(defaultValue = "${basedir}/package.json")
    private File target;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final String[] gcVersionLook = {""};
        project.getPluginArtifacts().forEach(artifact -> {
            if (artifact.getGroupId().equals("com.datathings") && artifact.getArtifactId().equals("greycat-mavenplugin")) {
                gcVersionLook[0] = artifact.getVersion();
            }
        });
        String gcVersion = gcVersionLook[0];
        getLog().info("detected GreyCat version " + gcVersion);
        final Charset charset = StandardCharsets.UTF_8;

        try {
            String content = new String(Files.readAllBytes(target.toPath()), charset);
            if (gcVersion.contains("-SNAPSHOT")) {
                MavenResolver resolver = new MavenResolver();
                HashSet<String> urls = new HashSet<String>();
                //TODO add all current repository
                urls.add("https://oss.sonatype.org/content/repositories/snapshots");
                File greycatTgz = resolver.resolve("com.datathings", "greycat", gcVersion, "tgz", urls);
                File greycatWebSocketTgz = resolver.resolve("com.datathings", "greycat-websocket", gcVersion, "tgz", urls);
                File greycatMLTgz = resolver.resolve("com.datathings", "greycat-ml", gcVersion, "tgz", urls);
                content = content
                        .replaceAll("\"greycat\": \".*\"", "\"greycat\": \"" + greycatTgz.getAbsolutePath() + "\"")
                        .replaceAll("\"greycat-websocket\": \".*\"", "\"greycat-websocket\": \"" + greycatWebSocketTgz.getAbsolutePath() + "\"")
                        .replaceAll("\"greycat-ml\": \".*\"", "\"greycat-ml\": \"" + greycatMLTgz.getAbsolutePath() + "\"");
                Files.write(target.toPath(), content.getBytes(charset));
            } else {
                while (gcVersion.split("\\.").length != 3) {
                    gcVersion += ".0";
                }
                content = content
                        .replaceAll("\"greycat\": \".*\"", "\"greycat\": \"" + gcVersion + "\"")
                        .replaceAll("\"greycat-websocket\": \".*\"", "\"greycat-websocket\": \"" + gcVersion + "\"")
                        .replaceAll("\"greycat-ml\": \".*\"", "\"greycat-ml\": \"" + gcVersion + "\"");
            }
            Files.write(target.toPath(), content.getBytes(charset));

            File yarnLock = new File(target.getParentFile(), "yarn.lock");
            if (yarnLock.exists()) {
                yarnLock.delete();
            }
            File packageLock = new File(target.getParentFile(), "package.lock");
            if (packageLock.exists()) {
                packageLock.delete();
            }
        } catch (Exception e) {
            getLog().error(e);
        }
    }

}
