package org.batamsingh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:mydatabase.db";

        try (Connection con = DriverManager.getConnection(url);
             Statement stm = con.createStatement()) {

            // Create a Table
            String createTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL" +
                    "age INTEGER)";

            stm.execute(createTable);
            System.out.println("User table created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}