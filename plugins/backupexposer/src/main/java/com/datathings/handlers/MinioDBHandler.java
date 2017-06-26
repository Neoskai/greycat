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

import com.google.gson.Gson;
import greycat.BackupOptions;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.util.ArrayList;
import java.util.List;

public class MinioDBHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        httpServerExchange.getResponseSender().send(getDatabase());
    }

    private String getDatabase(){
        try {
            MinioClient minioClient = new MinioClient(BackupOptions.minioPath(),
                    BackupOptions.accessKey(),
                    BackupOptions.secretKey());

            if (minioClient.bucketExists(BackupOptions.dbBucket())) {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(BackupOptions.dbBucket());
                List<String> finalItems = new ArrayList<>();

                for (Result<Item> result : myObjects){
                    finalItems.add(result.get().objectName());
                }

                return new Gson().toJson(finalItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
