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

import com.google.common.base.CharMatcher;
import com.google.gson.Gson;
import greycat.BackupOptions;
import greycat.backup.tools.FileKey;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.util.ArrayList;
import java.util.List;

public class MinioLogsHandler implements HttpHandler{
    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        httpServerExchange.getResponseSender().send(getLogs());
    }

    private String getLogs(){
        try {
            MinioClient minioClient = new MinioClient(BackupOptions.minioPath(),
                    BackupOptions.accessKey(),
                    BackupOptions.secretKey());

            if (minioClient.bucketExists(BackupOptions.bucket())) {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(BackupOptions.bucket(),"data/logs/", false);
                List<String> shardList = new ArrayList<>();

                List<String> logLapse = new ArrayList<>();

                for (Result<Item> result : myObjects){
                    String fileName = result.get().objectName();
                    shardList.add(fileName);
                }

                for (String shard : shardList){
                    Iterable<Result<Item>> shardItem = minioClient.listObjects(BackupOptions.bucket(),shard, false);

                    for (Result<Item> result : shardItem){
                        String fileName = result.get().objectName();
                        logLapse.add(fileName);
                    }
                }

                return new Gson().toJson(logLapse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
