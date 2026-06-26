package com.portfolioafam.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            System.err.println("[Config] Cannot load config.properties: " + e.getMessage());
        }
    }

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

    public static String getSmtpHost() {
        return props.getProperty("smtp.host", "smtp.gmail.com");
    }

    public static int getSmtpPort() {
        return Integer.parseInt(props.getProperty("smtp.port", "587"));
    }

    public static String getSmtpUsername() {
        return props.getProperty("smtp.username", "");
    }

    public static String getSmtpPassword() {
        return props.getProperty("smtp.password", "");
    }

    public static String getSmtpFrom() {
        return props.getProperty("smtp.from", "");
    }
}
