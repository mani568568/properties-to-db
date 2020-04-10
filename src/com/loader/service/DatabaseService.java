package com.loader.service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

    private String keyColumn = "";
    private String valueColumn = "";
    private String tableName = "";

    private static String username = "";
    private static String password = "";
    private static String url = "";

    public  void initialize(String username,
                                     String password,
                                     String url)
    {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public void loadMetaData(String keyColumn,
                           String valueColumn,
                           String tableName
                           ) {
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.tableName = tableName;
    }

    public boolean publishToDatabase(String keyData, String valueData) {
        boolean result = false;
        String insertSQL = generateInsert(keyData, valueData);
        // auto close connection and statement
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement statement = conn.createStatement()) {
            int row = statement.executeUpdate(insertSQL);
            if (row == 1) {
                result = true;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private String generateInsert(String keyData, String valueData) {

        return "INSERT INTO " + this.tableName + " (" + this.keyColumn + "," + this.valueColumn + ") " +
                "VALUES ('" + keyData + "','" + valueData + "')";

    }

}
