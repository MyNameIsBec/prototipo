package com.portfolioafam.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:postgresql://localhost:5432/portfolioafam?connectTimeout=3&loginTimeout=3&socketTimeout=5");
    private static final String USER = System.getenv().getOrDefault(
            "DB_USER", "postgres");
    private static final String PASSWORD = System.getenv().getOrDefault(
            "DB_PASSWORD", "postgres");

    private Connection connection;
    private boolean migrated;

    public DatabaseManager() {
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!migrated) {
                runMigrations();
                migrated = true;
            }
        }
        return connection;
    }

    private void runMigrations() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("ALTER TABLE studenti ADD COLUMN IF NOT EXISTS password_temporanea BOOLEAN NOT NULL DEFAULT FALSE");
        } catch (SQLException e) {
            System.err.println("[DB] Migration skipped: " + e.getMessage());
        }
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
