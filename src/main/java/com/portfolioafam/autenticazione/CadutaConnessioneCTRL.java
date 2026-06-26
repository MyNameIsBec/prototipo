package com.portfolioafam.autenticazione;

import com.portfolioafam.repository.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public class CadutaConnessioneCTRL {

    private static final int MAX_TENTATIVI = 10;

    private final DatabaseManager dbManager;

    public CadutaConnessioneCTRL(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean tentaRiconnessione() {
        int tentativi = 0;
        while (tentativi < MAX_TENTATIVI) {
            try {
                Connection conn = dbManager.getConnection();
                if (conn != null && !conn.isClosed()) {
                    return true;
                }
            } catch (SQLException e) {
                tentativi++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        return false;
    }
}
