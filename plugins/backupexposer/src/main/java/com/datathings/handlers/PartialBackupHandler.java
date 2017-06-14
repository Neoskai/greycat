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
import greycat.backup.fastloader.FastBackupLoader;
import greycat.rocksdb.RocksDBStorage;
import greycat.scheduler.NoopScheduler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class PartialBackupHandler implements HttpHandler{
    private String _basePath;
    private FastBackupLoader _loader;

    public PartialBackupHandler(String basePath){
        _basePath = basePath;
        String sparkeyPath = _basePath+ "/logs";

        Graph graph = new GraphBuilder()
                .withScheduler(new NoopScheduler())
                .withStorage(new RocksDBStorage(_basePath))
                .build();

        _loader = new FastBackupLoader(sparkeyPath, graph);
    }


    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        String startString = httpServerExchange.getQueryParameters().get("start").getFirst();
        Long startStamp = Long.parseLong(startString);

        String endString = httpServerExchange.getQueryParameters().get("end").getFirst();
        Long endStamp = Long.parseLong(endString);

        _loader.backupSequence(startStamp, endStamp);

        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        httpServerExchange.getResponseSender().send("Backup Done");
    }
}
