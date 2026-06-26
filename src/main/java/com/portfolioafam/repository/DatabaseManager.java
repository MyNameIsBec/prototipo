package com.portfolioafam.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:postgresql://localhost:5432/portfolioafam?connectTimeout=3&loginTimeout=3&socketTimeout=5");
    private static final String USER = System.getenv().getOrDefault(
            "DB_USER", "postgres");
    private static final String PASSWORD = System.getenv().getOrDefault(
            "DB_PASSWORD", "postgres");

    private Connection connection;

    public DatabaseManager() {
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
