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
import greycat.struct.BackupEntry;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MinioDBHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        httpServerExchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "*");
        httpServerExchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"),
                "GET, POST, PUT, DELETE, OPTIONS");
        httpServerExchange.getResponseSender().send(getDatabase());
    }

    private String getDatabase(){
        try {
            MinioClient minioClient = new MinioClient(BackupOptions.minioPath(),
                    BackupOptions.accessKey(),
                    BackupOptions.secretKey());

            if (minioClient.bucketExists(BackupOptions.dbBucket())) {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(BackupOptions.dbBucket(),"/backup/meta");
                List<String> fileList = new ArrayList<>();
                List<BackupEntry> entryList = new ArrayList<>();

                File temp = new File("temp");
                if(!temp.exists()){
                    temp.mkdir();
                }

                // Download the meta files internally
                for (Result<Item> result : myObjects){

                    String newName = "temp/" + result.get().objectName();
                    File finalFile = new File(newName);

                    File parent = finalFile.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException("Couldn't create dir: " + parent);
                    }

                    minioClient.getObject(BackupOptions.dbBucket(), result.get().objectName(), newName);
                    fileList.add(newName);
                }

                for(String file: fileList){
                    BackupEntry entry = new BackupEntry();

                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String timeString = reader.readLine();
                    String idString = file.substring(file.lastIndexOf("/")+1);

                    long timestamp = Long.parseLong(timeString);
                    long id = Long.parseLong(idString);

                    entry.setId(id);
                    entry.setTimestamp(timestamp);

                    entryList.add(entry);
                }

                deleteFile(new File("temp"));

                return new Gson().toJson(entryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static void deleteFile(File element) {
        if (element.isDirectory()) {
            for (File sub : element.listFiles()) {
                deleteFile(sub);
            }
        }
        element.delete();
    }


}
