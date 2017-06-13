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

package com.datathings;

import com.datathings.handlers.BackupHandler;
import com.datathings.handlers.FullBackupHandler;
import com.datathings.handlers.LogHandler;
import com.datathings.handlers.PartialBackupHandler;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;

import java.nio.file.Paths;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;

public class Server
{
    private Undertow _server;
    private int _port;

    private String _basePath;

    public Server(int port, String basePath){
        _port = port;
        _basePath = basePath;
    }

    public void start(){
        _server = Undertow.builder()
                .addHttpListener(_port, "0.0.0.0")
                .setHandler(
                        path()
                                .addPrefixPath("/backup", new BackupHandler(_basePath))
                                .addPrefixPath("/logs", new LogHandler(_basePath))
                                .addPrefixPath("/fullBackup", new FullBackupHandler(_basePath))
                                .addPrefixPath("/partialBackup", new PartialBackupHandler(_basePath))
                                .addPrefixPath("/file",
                                        resource(new PathResourceManager(Paths.get(_basePath), 100))
                                                .setDirectoryListingEnabled(true))

                ).build();
        _server.start();
    }

    public void stop() {
        _server.stop();
        _server = null;
    }


}
