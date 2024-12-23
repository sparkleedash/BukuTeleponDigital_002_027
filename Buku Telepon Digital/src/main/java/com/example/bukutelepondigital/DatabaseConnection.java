package com.example.bukutelepondigital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/phonebook";  // Default MySQL URL
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Set the connection to be used in tests (for SQLite)
    public static void setConnection(Connection conn) {
        connection = conn;
    }

    // Default: connects to MySQL
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;  // Use the test connection if set
    }
}
