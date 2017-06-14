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
}
