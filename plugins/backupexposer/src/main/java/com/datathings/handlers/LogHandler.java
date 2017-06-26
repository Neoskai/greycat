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

import com.datathings.util.FileUtil;
import com.google.gson.Gson;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogHandler implements HttpHandler{
    private String _basePath;

    public LogHandler(String basePath){
        _basePath = basePath;
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        httpServerExchange.getResponseSender().send(getJsonLogs());
    }

    private String getJsonLogs(){
        List<String> logFiles = new ArrayList<>();

        String path = _basePath + "/logs";
        logFiles = FileUtil.getFiles(logFiles, Paths.get(path));

        for(int i=0 ; i < logFiles.size(); i++){
            String newName = logFiles.get(i).substring(logFiles.get(i).indexOf("/data"));
            logFiles.set(i, newName);
        }

        return new Gson().toJson(logFiles);
    }
}
