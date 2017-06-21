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
package greycat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ignore ts
 */
public class BackupOptions {

    /**
     * Loads the number of elements in the Pool for Backups
     * @return Number of shards in pool
     */
    public static int poolSize(){
        Properties prop = new Properties();
        InputStream input;

        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return Integer.parseInt(prop.getProperty("backup.poolsize"));

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return Constants.POOLSIZE;
    }

    /**
     * Loads the number of thread backups should be using
     * @return Number of threads
     */
    public static int threadPool(){
        Properties prop = new Properties();
        InputStream input;

        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return Integer.parseInt(prop.getProperty("backup.threadpool"));

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return Constants.THREADPOOL;
    }

    /**
     * Loads the number of element we can process before saving into the new database during backups
     * @return Number of elements processed before a save
     */
    public static int savePoint(){
        Properties prop = new Properties();
        InputStream input;

        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return Integer.parseInt(prop.getProperty("backup.savepoint"));

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return Constants.SAVEPOINT;
    }

    /**
     * Loads the number of events that we can put in a single file before passing to the next one
     * @return Maximum number of elements
     */
    public static int maxEntry(){
        Properties prop = new Properties();
        InputStream input;

        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return Integer.parseInt(prop.getProperty("backup.maxentry"));

        } catch (IOException ex) {
            System.err.println("No configuration file");
        }

        return Constants.MAXENTRY;
    }

    /**
     * Loads the duration of a timelapse between 2 major Snapshots
     * @return Timelapse between 2 Snapshots
     */
    public static int timelapseDuration(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return Integer.parseInt(prop.getProperty("backup.timelapse"));

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return Constants.TIMELAPSEDURATION;
    }

    /**
     * Returns the address of the Minio server used (Distributed FS)
     * @return Address of the minio server
     */
    public static String minioPath(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return prop.getProperty("minio.path");

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return "http://localhost:9000";
    }

    /**
     * Returns the access key used to connect to minio client
     * @return Access Key
     */
    public static String accessKey(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return prop.getProperty("minio.access");

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return "QZEIXYIYB22HADEYYC1X";
    }

    /**
     * Returns the secret key used to connect to minio client
     * @return Secret key
     */
    public static String secretKey(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return prop.getProperty("minio.secret");

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return "15d4CYxNHAR12tKjxN/1q5HIgIo4KbzC1twzozwZ";
    }

    /**
     * Returns the main bucket to use by minio to save the backup files
     * @return Name of the bucket
     */
    public static String bucket(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return prop.getProperty("minio.bucket");

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return "logs";
    }

    /**
     * Returns the main bucket to use by minio to save the database backup files
     * @return Name of the bucket
     */
    public static String dbBucket(){
        Properties prop = new Properties();
        InputStream input;
        try {
            input = BackupOptions.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);

            return prop.getProperty("minio.dbbucket");

        } catch (Exception ex) {
            System.err.println("No configuration file");
        }

        return "database";
    }
}
