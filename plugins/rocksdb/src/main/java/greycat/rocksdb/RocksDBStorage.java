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
package greycat.rocksdb;

import greycat.BackupOptions;
import greycat.Callback;
import greycat.Constants;
import greycat.Graph;
import greycat.struct.BackupEntry;
import greycat.plugin.Storage;
import greycat.struct.Buffer;
import greycat.struct.BufferIterator;
import greycat.utility.Base64;
import greycat.utility.HashHelper;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.apache.logging.log4j.core.util.FileUtils;
import org.rocksdb.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RocksDBStorage implements Storage {

    private Options _options;

    private RocksDB _db;

    private Graph _graph;
    private final List<Callback<Buffer>> updates = new ArrayList<Callback<Buffer>>();

    private static final String _connectedError = "PLEASE CONNECT YOUR DATABASE FIRST";

    private boolean _isConnected = false;

    private final String _storagePath;

    public RocksDBStorage(String storagePath) {
        if (System.getProperty("os.arch").equals("arm")) {
            LibraryLoader.loadArmLibrary("librocksdbjni-linux32");
        }
        RocksDB.loadLibrary();
        this._storagePath = storagePath;
    }

    @Override
    public void listen(Callback<Buffer> synCallback) {
        updates.add(synCallback);
    }

    @Override
    public BackupEntry createBackup(boolean isFromBackup) {
        try {
            retrieveLastBackup();

            _graph.save(null);

            File backupFolder = new File(_storagePath + "/backup");
            if(!backupFolder.exists()){
                backupFolder.mkdir();
            }

            BackupableDBOptions options = new BackupableDBOptions( _storagePath + "/backup");
            options.setShareTableFiles(false);
            options.setShareFilesWithChecksum(false);

            BackupEngine backupEngine = BackupEngine.open( Env.getDefault(), options);

            backupEngine.createNewBackup(_db);

            // @TODO : Find Metadata adaptation to Java or add external mark for Snapshot created from Backup process

            BackupInfo info = backupEngine.getBackupInfo().get(backupEngine.getBackupInfo().size()-1);
            BackupEntry entry = new BackupEntry();

            entry.setId(info.backupId());
            entry.setTimestamp(info.timestamp());

            try {
                MinioClient minioClient = new MinioClient(BackupOptions.minioPath(),
                        BackupOptions.accessKey(),
                        BackupOptions.secretKey());

                if(!minioClient.bucketExists(BackupOptions.dbBucket())) {
                    minioClient.makeBucket(BackupOptions.dbBucket());
                }

                // Uploading Meta File
                String metaFile = _storagePath + "/backup/meta/" + info.backupId();
                minioClient.putObject(BackupOptions.dbBucket(), metaFile.substring(metaFile.indexOf("/backup")), metaFile);

                //Uploading Backup Folder
                List<String> localObjects = new ArrayList<>();
                localObjects = getFiles(localObjects, Paths.get(backupFolder.getPath()+ "/private/" + info.backupId()));

                for(String local : localObjects){
                    minioClient.putObject(BackupOptions.dbBucket(), local.substring(local.lastIndexOf("/backup")), local);
                }

            } catch (Exception e){
                System.err.println("Couldn't upload file to server");
            }

            return entry;
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * List all files in a folder
     * @param fileNames List to fill in
     * @param dir Path of the dir
     * @return THe filled list
     */
    private List<String> getFiles(List<String> fileNames, Path dir) {
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    getFiles(fileNames, path);
                } else {
                    fileNames.add(path.toAbsolutePath().toString());
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    @Override
    public BackupEntry loadLatestBackup() {
        try {
            retrieveLastBackup();

            BackupEngine backupEngine = BackupEngine.open( Env.getDefault(), new BackupableDBOptions(_storagePath + "/backup"));
            backupEngine.restoreDbFromLatestBackup(_storagePath + "/data", _storagePath + "/data", new RestoreOptions(true));

            BackupInfo info = backupEngine.getBackupInfo().get(backupEngine.getBackupInfo().size()-1);
            BackupEntry entry = new BackupEntry();

            entry.setId(info.backupId());
            entry.setTimestamp(info.timestamp());

            return entry;
        } catch (RocksDBException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException arg){
            System.err.println("No backup found");
        }

        return null;
    }

    /**
     * Downloads the latest backup if it is on the configured DFS
     */
    private void retrieveLastBackup(){
        try {
            int topLocal = -1;
            int topExternal = -1;

            // Retrieve the highest local backup
            List<String> localBackups = new ArrayList<>();
            File backupFolder = new File(_storagePath + "/backup/meta");

            if(backupFolder.exists()){
                getFiles(localBackups, Paths.get(backupFolder.getPath()));

                for (String local : localBackups) {
                    String fileNumber = local.substring(local.lastIndexOf("/") + 1, local.length());
                    if (Integer.parseInt(fileNumber) > topLocal) {
                        topLocal = Integer.parseInt(fileNumber);
                    }
                }
            }

            // Compare with the highest backup in the bucket
            MinioClient minioClient = new MinioClient(BackupOptions.minioPath(),
                    BackupOptions.accessKey(),
                    BackupOptions.secretKey());

            if (!minioClient.bucketExists(BackupOptions.dbBucket())) {
                minioClient.makeBucket(BackupOptions.dbBucket());
            }

            Iterable<Result<Item>> myObjects = minioClient.listObjects(BackupOptions.dbBucket(), "backup/meta");

            for (Result<Item> result : myObjects) {
                Item item = result.get();
                String numberName = item.objectName().substring(item.objectName().lastIndexOf("/")+1, item.objectName().length());
                if( Integer.parseInt(numberName) > topExternal){
                    topExternal = Integer.parseInt(numberName);
                }
            }

            // If the latest backup is on DFS
            if(topExternal > topLocal){
                String metaName =   "backup/meta/" + topExternal;

                File metaFile = new File(_storagePath + "/" + metaName);
                File metaParent = metaFile.getParentFile();
                if (!metaParent.exists() && !metaParent.mkdirs()) {
                    throw new IllegalStateException("Couldn't create dir: " + metaParent);
                }

                minioClient.getObject(BackupOptions.dbBucket(), metaName, _storagePath + "/" + metaName);

                Iterable<Result<Item>> backupElems = minioClient.listObjects(BackupOptions.dbBucket(), "backup/private/"+topExternal);
                for (Result<Item> result : backupElems) {
                    Item item = result.get();
                    String name = item.objectName();

                    File file = new File(_storagePath + "/" +  name);
                    File parent = file.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IllegalStateException("Couldn't create dir: " + parent);
                    }

                    minioClient.getObject(BackupOptions.dbBucket(), name, _storagePath+ "/" + name);
                }
            }
        } catch (Exception e){
            System.err.println("Could not retrieve last backup from DFS");
        }


    }

    @Override
    public BackupEntry loadBackup(long id) {
        try {
            BackupEngine backupEngine = BackupEngine.open( Env.getDefault(), new BackupableDBOptions(_storagePath + "/backup"));
            backupEngine.restoreDbFromBackup((int) id, _storagePath + "/data", _storagePath + "/data", new RestoreOptions(true));

            BackupInfo info = backupEngine.getBackupInfo().get((int) id);
            BackupEntry entry = new BackupEntry();

            entry.setId(info.backupId());
            entry.setTimestamp(info.timestamp());

            return entry;
        } catch (RocksDBException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException arg){
            System.err.println("No backup found");
        }

        return null;
    }

    @Override
    public void get(Buffer keys, Callback<Buffer> callback) {
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        final Buffer result = _graph.newBuffer();
        final BufferIterator it = keys.iterator();
        /*
        List<byte[]> query = new ArrayList<byte[]>();
        while (it.hasNext()) {
            Buffer view = it.next();
            query.add(view.data());
        }
        try {
            Map<byte[], byte[]> dbResult = _db.multiGet(query);
            boolean isFirst = true;
            for (int i = 0; i < query.size(); i++) {
                if (!isFirst) {
                    result.write(Constants.BUFFER_SEP);
                } else {
                    isFirst = false;
                }
                byte[] subResult = dbResult.get(query.get(i));
                if (subResult != null) {
                    result.writeAll(subResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (callback != null) {
            callback.on(result);
        }
        */

        boolean isFirst = true;
        while (it.hasNext()) {
            Buffer view = it.next();
            try {
                if (!isFirst) {
                    result.write(Constants.BUFFER_SEP);
                } else {
                    isFirst = false;
                }
                byte[] res = _db.get(view.data());
                if (res != null) {
                    result.writeAll(res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (callback != null) {
            callback.on(result);
        }

    }

    @Override
    public void put(Buffer stream, Callback<Boolean> p_callback) {
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        Buffer result = null;
        if (updates.size() != 0) {
            result = _graph.newBuffer();
        }
        WriteBatch batch = new WriteBatch();
        BufferIterator it = stream.iterator();
        boolean isFirst = true;
        while (it.hasNext()) {
            Buffer keyView = it.next();
            Buffer valueView = it.next();
            if (valueView != null) {
                batch.put(keyView.data(), valueView.data());
            }
            if (result != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    result.write(Constants.KEY_SEP);
                }
                result.writeAll(keyView.data());
                result.write(Constants.KEY_SEP);
                Base64.encodeLongToBuffer(HashHelper.hashBuffer(valueView, 0, valueView.length()), result);
            }
        }
        WriteOptions options = new WriteOptions();
        options.setSync(false);
        try {
            _db.write(options, batch);
            for (int i = 0; i < updates.size(); i++) {
                final Callback<Buffer> explicit = updates.get(i);
                explicit.on(result);
            }
            if (p_callback != null) {
                p_callback.on(true);
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            if (p_callback != null) {
                p_callback.on(false);
            }
        }
    }

    @Override
    public final void putSilent(Buffer stream, Callback<Buffer> callback) {
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        Buffer result = _graph.newBuffer();
        WriteBatch batch = new WriteBatch();
        BufferIterator it = stream.iterator();
        boolean isFirst = true;
        while (it.hasNext()) {
            Buffer keyView = it.next();
            Buffer valueView = it.next();
            if (valueView != null) {
                batch.put(keyView.data(), valueView.data());
            }
            if (isFirst) {
                isFirst = false;
            } else {
                result.write(Constants.KEY_SEP);
            }
            result.writeAll(keyView.data());
            result.write(Constants.KEY_SEP);
            Base64.encodeLongToBuffer(HashHelper.hashBuffer(valueView, 0, valueView.length()), result);
        }
        WriteOptions options = new WriteOptions();
        options.setSync(false);
        try {
            _db.write(options, batch);
            for (int i = 0; i < updates.size(); i++) {
                final Callback<Buffer> explicit = updates.get(i);
                explicit.on(result);
            }
            callback.on(result);
        } catch (RocksDBException e) {
            e.printStackTrace();
            callback.on(null);
        }
    }

    @Override
    public void remove(Buffer keys, Callback<Boolean> callback) {
        if (!_isConnected) {
            throw new RuntimeException(_connectedError);
        }
        try {
            BufferIterator it = keys.iterator();
            while (it.hasNext()) {
                Buffer view = it.next();
                _db.delete(view.data());
            }
            if (callback != null) {
                callback.on(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

    @Override
    public void disconnect(Callback<Boolean> callback) {
        //TODO write the prefix
        try {
            WriteOptions options = new WriteOptions();
            options.sync();
            _db.write(options, new WriteBatch());
            _db.close();
            _options.close();
            _options = null;
            _db = null;
            _isConnected = false;
            if (callback != null) {
                callback.on(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

    private static final byte[] prefixKey = "prefix".getBytes();

    @Override
    public void connect(Graph graph, Callback<Boolean> callback) {
        if (_isConnected) {
            if (callback != null) {
                callback.on(true);
            }
            return;
        }
        _graph = graph;
        //by default activate snappy compression of bytes
        _options = new Options()
                .setCreateIfMissing(true)
                .setCompressionType(CompressionType.SNAPPY_COMPRESSION);
        File location = new File(_storagePath);
        if (!location.exists()) {
            location.mkdirs();
        }
        File targetDB = new File(location, "data");
        targetDB.mkdirs();
        try {
            _db = RocksDB.open(_options, targetDB.getAbsolutePath());
            _isConnected = true;
            if (callback != null) {
                callback.on(true);
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(false);
            }
        }
    }

    @Override
    public void lock(Callback<Buffer> callback) {
        try {
            byte[] current = _db.get(prefixKey);
            if (current == null) {
                current = new String("0").getBytes();
            }
            Short currentPrefix = Short.parseShort(new String(current));
            _db.put(prefixKey, ((currentPrefix + 1) + "").getBytes());
            if (callback != null) {
                Buffer newBuf = _graph.newBuffer();
                Base64.encodeIntToBuffer(currentPrefix, newBuf);
                callback.on(newBuf);
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.on(null);
            }
        }
    }

    @Override
    public void unlock(Buffer previousLock, Callback<Boolean> callback) {
        //noop
        callback.on(true);
    }
}
