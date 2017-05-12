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

package greycat.backup.tools;

import java.sql.*;

public class H2Storage {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private Connection _connection;

    public H2Storage(){
        _connection = getDBConnection();
    }

    /**
     * Creates tables for database init
     */
    public void createDatabase(){
        try {
            String createHashQuery = "CREATE TABLE FILE(hash int PRIMARY KEY, name VARCHAR(255))";
            PreparedStatement createHashStatement = _connection.prepareStatement(createHashQuery);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Inserts an hash to the database
     * @param fileName String name fo the file
     * @param hash Hashed version of the filename
     */
    public void insertHash(String fileName, int hash){
        try {
            String insertHashQuery = "INSERT INTO FILE" + "(hash,name) values" + "(?,?)";
            PreparedStatement insertHashStatement = _connection.prepareStatement(insertHashQuery);

            insertHashStatement.setInt(1, hash);
            insertHashStatement.setString(2, fileName);

            insertHashStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the name of the file for the given hash
     * @param hash Hash of the File
     * @return String name of the file
     */
    public String retrieveFile(int hash){
        try {
            String insertHashQuery = "SELECT name FROM FILE WHERE hash= ?";
            PreparedStatement selectHashStatement = _connection.prepareStatement(insertHashQuery);
            selectHashStatement.setInt(1,hash);
            ResultSet result = selectHashStatement.executeQuery();

            return result.getString("name");

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Drops the database
     */
    public void cleanDatabase(){
        try {
            String dropHashQuery = "DROP TABLE FILE";
            PreparedStatement dropHashStatement = _connection.prepareStatement(dropHashQuery);

            dropHashStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the connection object to connect to H2 DB
     * @return Connection done to the database
     */
    private static Connection getDBConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }
}
