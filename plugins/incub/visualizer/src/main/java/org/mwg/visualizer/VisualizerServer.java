package org.mwg.visualizer;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

public class VisualizerServer  {
    private static String urltoConnect = null;
    private static int serverPort = 8080;
    private static final String serverUrl = "0.0.0.0";

    private static void printHelp() {
        System.err.println("Usage:\n" +
                "Print help: java -jar <jarFile>\n" +
                "Launch: java -jar <jarFile> [<serverPort> <graphUrl>]\n" +
                "Default value: serverPort=" + serverPort + "; graphUrl=" + urltoConnect);
    }

    public static void main(String[] args) {
        if(args.length == 2) {
            try {
                serverPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println(ex.getMessage());
                printHelp();
                System.exit(2);
            }
            urltoConnect = args[1];
        } else if(args.length != 0) {
            printHelp();
            return;
        }


        Undertow server = Undertow.builder()
                .addHttpListener(serverPort,serverUrl)
                .setHandler(
                        Handlers.path(
                                Handlers.resource(new ClassPathResourceManager(VisualizerServer.class.getClassLoader()))
                        )
                )
                .build();


        server.start();

        StringBuilder goToBuilder = new StringBuilder();
        goToBuilder.append("http://")
                .append(serverUrl)
                .append(":")
                .append(serverPort);
        if(urltoConnect != null) {
            goToBuilder.append("?q=")
                    .append(urltoConnect);
        }

        System.out.println("Go to: " + goToBuilder);

    }
}
