package com.portfolioafam.app;

import com.portfolioafam.repository.DatabaseManager;
import com.portfolioafam.util.PasswordUtils;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSeeder {

    public static void main(String[] args) {
        System.out.println("=== Portfolio AFAM - Database Seeder ===");
        DatabaseManager dbManager = new DatabaseManager();

        try {
            Connection c = dbManager.getConnection();
            Statement stmt = c.createStatement();

            String adminHash = PasswordUtils.hashPassword("Admin123!@#");
            stmt.execute(
                "INSERT INTO amministratori (email, hash_password, email2fa) " +
                "VALUES ('admin@afam.it', '" + adminHash + "', 'admin2fa@afam.it') " +
                "ON CONFLICT (email) DO NOTHING");
            System.out.println("  Amministratore creato: admin@afam.it / Admin123!@#");

            String studentHash = PasswordUtils.hashPassword("Password1!");
            stmt.execute(
                "INSERT INTO studenti (CF, nome, cognome, email, hash_password, email2fa, " +
                "dati_accademici, visibilita_profilo) " +
                "VALUES ('RSSMRA85M01A271X', 'Mario', 'Rossi', 'mario.rossi@afam.it', " +
                "'" + studentHash + "', 'mario.rossi@studenti.afam.it', " +
                "'Pianoforte - Conservatorio - A.A. 2025/26', 'PUBBLICO') " +
                "ON CONFLICT (CF) DO NOTHING");
            System.out.println("  Studente creato: mario.rossi@afam.it / Password1!");

            stmt.execute(
                "INSERT INTO cartelle (CF, nome_cartella, privacy) " +
                "VALUES ('RSSMRA85M01A271X', 'Esami di pianoforte', 'PUBBLICO') " +
                "ON CONFLICT DO NOTHING");
            stmt.execute(
                "INSERT INTO cartelle (CF, nome_cartella, privacy) " +
                "VALUES ('RSSMRA85M01A271X', 'Progetti personali', 'PRIVATO') " +
                "ON CONFLICT DO NOTHING");
            System.out.println("  Cartelle di esempio create");

            stmt.execute(
                "INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, " +
                "privacy) VALUES ('RSSMRA85M01A271X', 1, 'Saggio finale.pdf', 'PDF', '\\x', 0, " +
                "'PUBBLICO')");
            stmt.execute(
                "INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, " +
                "privacy) VALUES ('RSSMRA85M01A271X', 1, 'Improvvisazione jazz.mp3', 'AUDIO', " +
                "'\\x', 0, 'PUBBLICO')");
            stmt.execute(
                "INSERT INTO contenuti (CF, nome, tipo, file_dati, dimensione, privacy) " +
                "VALUES ('RSSMRA85M01A271X', 'Bozza privata.pdf', 'PDF', '\\x', 0, 'PRIVATO')");
            System.out.println("  Contenuti di esempio creati");

            stmt.close();
            dbManager.close();
            System.out.println("Database popolato con successo!");
        } catch (Exception e) {
            System.err.println("ERRORE: " + e.getMessage());
            System.err.println("Verifica che PostgreSQL sia in esecuzione e che il database");
            System.err.println("'portfolioafam' esista con lo schema creato.");
            System.err.println("Esegui: psql -U postgres -d portfolioafam -f src/main/resources/sql/schema.sql");
            System.exit(1);
        }
    }
}
