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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackupHandler implements HttpHandler{
    private String _basePath;

    public BackupHandler(String basePath){
        _basePath = basePath;
    }

    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        httpServerExchange.getResponseSender().send(getJsonBackups());
    }

    private String getJsonBackups(){
        Map<Long, Long> backups = new HashMap<>();

        List<String> files = new ArrayList<>();
        String path = _basePath + "/backup/meta";
        files = FileUtil.getFiles(files, Paths.get(path));

        for (String file : files){
            String backupNumber = file.substring(file.lastIndexOf("/")+1);

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                backups.put(Long.parseLong(backupNumber), Long.parseLong(br.readLine()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Gson().toJson(backups);
    }
}
