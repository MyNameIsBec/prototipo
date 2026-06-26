package com.portfolioafam.util;

public class Config {

    private Config() {
    }

    public static boolean is2faEnabled() {
        return Boolean.parseBoolean(
                System.getProperty("portfolio.2fa.enabled", "true"));
    }

    public static String getDbUrl() {
        return System.getenv().getOrDefault(
                "DB_URL", "jdbc:postgresql://localhost:5432/portfolioafam?connectTimeout=3&loginTimeout=3&socketTimeout=5");
    }

    public static String getDbUser() {
        return System.getenv().getOrDefault("DB_USER", "postgres");
    }

    public static String getDbPassword() {
        return System.getenv().getOrDefault("DB_PASSWORD", "postgres");
    }
}
