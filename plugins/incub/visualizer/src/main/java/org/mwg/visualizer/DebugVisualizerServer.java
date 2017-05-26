package org.mwg.visualizer;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;

import java.nio.file.Paths;

public class DebugVisualizerServer {
    public static void main(String[] args) {

        final String urltoConnect = "ws://localhost:5678";
        final String serverUrl = "0.0.0.0";
        final int serverPort = 8080;

        Undertow server = Undertow.builder()
                .addHttpListener(serverPort,serverUrl)
                .setHandler(
                        Handlers.path(
                                Handlers.resource(
                                        new PathResourceManager(Paths.get("plugins/visualizer/src/main/resources").toAbsolutePath(),0)
                                )
                        )
                )
                .build();


        server.start();

        StringBuilder goToBuilder = new StringBuilder();
        goToBuilder.append("http://")
                .append(serverUrl)
                .append(":")
                .append(serverPort)
                .append("?q=")
                .append(urltoConnect);
        System.out.println("Go to: " + goToBuilder);


        System.out.println();

    }
}
