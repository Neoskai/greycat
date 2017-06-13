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

package com.datathings.handlers;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.backup.CrossBackup;
import greycat.rocksdb.RocksDBStorage;
import greycat.scheduler.NoopScheduler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class FullBackupHandler implements HttpHandler{
    private String _basePath;

    public FullBackupHandler(String basePath){
        _basePath = basePath;
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        String rocksPath = _basePath;
        String sparkeyPath = rocksPath+ "/logs";

        Graph graph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage(rocksPath))
                .build();

        CrossBackup.loadBackup(graph, sparkeyPath);

        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        httpServerExchange.getResponseSender().send("Backup Done");
    }
}
