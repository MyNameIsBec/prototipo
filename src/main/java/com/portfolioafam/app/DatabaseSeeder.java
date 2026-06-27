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

            stmt.execute("TRUNCATE TABLE segnalazioni, link, utenti_esterni, contenuti, cartelle, amministratori, studenti RESTART IDENTITY CASCADE");
            System.out.println("  Tabelle pulite (sequence resettate)");

            // ── Amministratori ──
            String adminHash = PasswordUtils.hashPassword("Admin123!@#");
            stmt.execute("INSERT INTO amministratori (email, hash_password, email2fa) " +
                "VALUES ('admin@afam.it', '" + adminHash + "', 'admin@afam.it')");
            stmt.execute("INSERT INTO amministratori (email, hash_password, email2fa) " +
                "VALUES ('superadmin@afam.it', '" + PasswordUtils.hashPassword("SuperAdmin1!") + "', 'superadmin@afam.it')");
            System.out.println("  Amministratori inseriti");

            // ── Studenti (8) ──
            String h1 = PasswordUtils.hashPassword("Password1!ab");
            String h2 = PasswordUtils.hashPassword("Password2!ab");
            String h3 = PasswordUtils.hashPassword("Password3!ab");
            String h4 = PasswordUtils.hashPassword("Password4!ab");
            String h5 = PasswordUtils.hashPassword("Password5!ab");
            String h6 = PasswordUtils.hashPassword("Password6!ab");
            String h7 = PasswordUtils.hashPassword("Password7!ab");
            String h8 = PasswordUtils.hashPassword("Password8!ab");

            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('RSSMRA85M01A271X', 'Mario', 'Rossi', 'mario.rossi@afam.it', '" + h1 + "', '+393351234567', 'mario.rossi@afam.it', 'Pianoforte - Conservatorio - A.A. 2025/26', 'PUBBLICO')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('VRDLGI90M15F205X', 'Luigi', 'Verdi', 'luigi.verdi@afam.it', '" + h2 + "', '+393352345678', 'luigi.verdi@afam.it', 'Violino - Conservatorio - A.A. 2024/25', 'PUBBLICO')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('BNCSRA92M41H123X', 'Sara', 'Bianchi', 'sara.bianchi@afam.it', '" + h3 + "', '+393353456789', 'sara.bianchi@afam.it', 'Canto - Accademia di Belle Arti - A.A. 2025/26', 'PUBBLICO')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('GLLNTN95M01L736X', 'Antonio', 'Galli', 'antonio.galli@afam.it', '" + h4 + "', '+393354567890', 'antonio.galli@afam.it', 'Chitarra - Conservatorio - A.A. 2023/24', 'PUBBLICO')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('RSSMRA88M01A271X', 'Maria', 'Rossi', 'maria.rossi@afam.it', '" + h5 + "', '+393355678901', 'maria.rossi@afam.it', 'Flauto - Conservatorio - A.A. 2025/26', 'PUBBLICO')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('CLNPLC91M01G573X', 'Paolo', 'Colombo', 'paolo.colombo@afam.it', '" + h6 + "', '+393356789012', 'paolo.colombo@afam.it', 'Percussioni - Accademia di Belle Arti - A.A. 2024/25', 'PUBBLICO')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('MSSLRR94M01L219X', 'Laura', 'Mussi', 'laura.mussi@afam.it', '" + h7 + "', '+393357890123', 'laura.mussi@afam.it', 'Composizione - Conservatorio - A.A. 2025/26', 'PREMIUM')");
            stmt.execute("INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, dati_accademici, visibilita_profilo) " +
                "VALUES ('FNTMRC87M01D548X', 'Marco', 'Fontana', 'marco.fontana@afam.it', '" + h8 + "', '+393358901234', 'marco.fontana@afam.it', 'Pianoforte - Accademia di Belle Arti - A.A. 2023/24', 'PRIVATO')");
            System.out.println("  8 studenti inseriti");

            // ── Cartelle ──
            // Mario Rossi (RSSMRA85M01A271X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA85M01A271X', 'Esami di pianoforte', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA85M01A271X', 'Progetti personali', 'PRIVATO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA85M01A271X', 'Concerti', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA85M01A271X', 'Materiale didattico', 'PUBBLICO')");
            // Luigi Verdi (VRDLGI90M15F205X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('VRDLGI90M15F205X', 'Studi violino', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('VRDLGI90M15F205X', 'Orchestra', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('VRDLGI90M15F205X', 'Bozze private', 'PRIVATO')");
            // Sara Bianchi (BNCSRA92M41H123X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('BNCSRA92M41H123X', 'Arie', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('BNCSRA92M41H123X', 'Esercizi di canto', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('BNCSRA92M41H123X', 'Registrazioni private', 'PRIVATO')");
            // Antonio Galli (GLLNTN95M01L736X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('GLLNTN95M01L736X', 'Repertorio chitarra', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('GLLNTN95M01L736X', 'Composizioni', 'PUBBLICO')");
            // Maria Rossi (RSSMRA88M01A271X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA88M01A271X', 'Flauto traverso', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA88M01A271X', 'Musica da camera', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('RSSMRA88M01A271X', 'Prove', 'PRIVATO')");
            // Paolo Colombo (CLNPLC91M01G573X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('CLNPLC91M01G573X', 'Percussioni', 'PUBBLICO')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('CLNPLC91M01G573X', 'Laboratori', 'PUBBLICO')");
            // Laura Mussi (MSSLRR94M01L219X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('MSSLRR94M01L219X', 'Partiture', 'PREMIUM')");
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('MSSLRR94M01L219X', 'Progetti compositivi', 'PREMIUM')");
            // Marco Fontana (FNTMRC87M01D548X)
            stmt.execute("INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES ('FNTMRC87M01D548X', 'Documenti personali', 'PRIVATO')");
            System.out.println("  21 cartelle inserite");

            // ── Contenuti ──
            // Mario: cartella 1 (Esami pianoforte, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 1, 'Saggio finale.pdf', 'PDF', '\\x', 245000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 1, 'Programma esame.pdf', 'PDF', '\\x', 120000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 1, 'Sonata K.331.mp3', 'AUDIO', '\\x', 5200000, 'PUBBLICO')");
            // Mario: cartella 2 (Progetti personali, PRIVATO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 2, 'Bozza privata.pdf', 'PDF', '\\x', 89000, 'PRIVATO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 2, 'Diario di bordo.txt', 'DOC', '\\x', 12000, 'PRIVATO')");
            // Mario: cartella 3 (Concerti, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 3, 'Concerto Beethoven.mp4', 'VIDEO', '\\x', 45000000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 3, 'Locandina concerto.jpg', 'IMAGE', '\\x', 3400000, 'PUBBLICO')");
            // Mario: cartella 4 (Materiale didattico, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 4, 'Scale e arpeggi.pdf', 'PDF', '\\x', 56000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 4, 'Esercizi tecnici.pdf', 'PDF', '\\x', 72000, 'PUBBLICO')");

            // Luigi: cartella 5 (Studi violino, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 5, 'Capricci Paganini.mp3', 'AUDIO', '\\x', 3800000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 5, 'Metodo Kreutzer.pdf', 'PDF', '\\x', 450000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 5, 'Registrazione lezione.mp3', 'AUDIO', '\\x', 6200000, 'PUBBLICO')");
            // Luigi: cartella 6 (Orchestra, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 6, 'Prova orchestra.mp4', 'VIDEO', '\\x', 78000000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 6, 'Partitura Mozart.pdf', 'PDF', '\\x', 280000, 'PUBBLICO')");
            // Luigi: cartella 7 (Bozze private, PRIVATO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 7, 'Composizione segreta.pdf', 'PDF', '\\x', 95000, 'PRIVATO')");

            // Sara: cartella 8 (Arie, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('BNCSRA92M41H123X', 8, 'Aria Traviata.mp3', 'AUDIO', '\\x', 4200000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('BNCSRA92M41H123X', 8, 'Aria Boheme.mp3', 'AUDIO', '\\x', 3900000, 'PUBBLICO')");
            // Sara: cartella 9 (Esercizi canto, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('BNCSRA92M41H123X', 9, 'Vocalizzi.pdf', 'PDF', '\\x', 34000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('BNCSRA92M41H123X', 9, 'Esercizi respirazione.pdf', 'PDF', '\\x', 28000, 'PUBBLICO')");
            // Sara: cartella 10 (Registrazioni private, PRIVATO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('BNCSRA92M41H123X', 10, 'Audizione privata.mp3', 'AUDIO', '\\x', 5100000, 'PRIVATO')");

            // Antonio: cartella 11 (Repertorio chitarra, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('GLLNTN95M01L736X', 11, 'Asturias.mp3', 'AUDIO', '\\x', 4800000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('GLLNTN95M01L736X', 11, 'Recuerdos Alhambra.mp3', 'AUDIO', '\\x', 3500000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('GLLNTN95M01L736X', 11, 'Tablature.pdf', 'PDF', '\\x', 150000, 'PUBBLICO')");
            // Antonio: cartella 12 (Composizioni, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('GLLNTN95M01L736X', 12, 'Composizione originale.pdf', 'PDF', '\\x', 210000, 'PUBBLICO')");

            // Maria: cartella 13 (Flauto traverso, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA88M01A271X', 13, 'Studio flauto.mp3', 'AUDIO', '\\x', 2900000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA88M01A271X', 13, 'Metodo flauto.pdf', 'PDF', '\\x', 330000, 'PUBBLICO')");
            // Maria: cartella 14 (Musica da camera, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA88M01A271X', 14, 'Quintetto Mozart.mp4', 'VIDEO', '\\x', 62000000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA88M01A271X', 14, 'Programma sala.pdf', 'PDF', '\\x', 67000, 'PUBBLICO')");
            // Maria: cartella 15 (Prove, PRIVATO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA88M01A271X', 15, 'Prova registro.mp3', 'AUDIO', '\\x', 1800000, 'PRIVATO')");

            // Paolo: cartella 16 (Percussioni, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('CLNPLC91M01G573X', 16, 'Ritmi africani.mp3', 'AUDIO', '\\x', 4100000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('CLNPLC91M01G573X', 16, 'Tecnica tamburo.pdf', 'PDF', '\\x', 180000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('CLNPLC91M01G573X', 16, 'Concerto percussioni.mp4', 'VIDEO', '\\x', 55000000, 'PUBBLICO')");
            // Paolo: cartella 17 (Laboratori, PUBBLICO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('CLNPLC91M01G573X', 17, 'Relazione laboratorio.pdf', 'PDF', '\\x', 92000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('CLNPLC91M01G573X', 17, 'Presentazione.pptx', 'DOC', '\\x', 2100000, 'PUBBLICO')");

            // Laura: cartella 18 (Partiture, PREMIUM)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('MSSLRR94M01L219X', 18, 'Partitura sinfonica.pdf', 'PDF', '\\x', 1200000, 'PREMIUM')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('MSSLRR94M01L219X', 18, 'Voce pianoforte.pdf', 'PDF', '\\x', 450000, 'PREMIUM')");
            // Laura: cartella 19 (Progetti compositivi, PREMIUM)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('MSSLRR94M01L219X', 19, 'Bozza quartetti.pdf', 'PDF', '\\x', 340000, 'PREMIUM')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('MSSLRR94M01L219X', 19, 'Schizzi compositivi.pdf', 'PDF', '\\x', 190000, 'PREMIUM')");

            // Marco: cartella 20 (Documenti personali, PRIVATO)
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('FNTMRC87M01D548X', 20, 'Curriculum.pdf', 'PDF', '\\x', 110000, 'PRIVATO')");
            stmt.execute("INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES ('FNTMRC87M01D548X', 20, 'Lettera motivazionale.pdf', 'PDF', '\\x', 45000, 'PRIVATO')");

            System.out.println("  43 contenuti inseriti");

            // ── Contenuti fuori cartella (con id_cartella NULL) ──
            stmt.execute("INSERT INTO contenuti (CF, nome, tipo, file_dati, dimensione, privacy) VALUES ('RSSMRA85M01A271X', 'Selfie concerto.jpg', 'IMAGE', '\\x', 2800000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, nome, tipo, file_dati, dimensione, privacy) VALUES ('VRDLGI90M15F205X', 'Foto violino.jpg', 'IMAGE', '\\x', 3100000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, nome, tipo, file_dati, dimensione, privacy) VALUES ('BNCSRA92M41H123X', 'Foto ritratto.jpg', 'IMAGE', '\\x', 2200000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, nome, tipo, file_dati, dimensione, privacy) VALUES ('GLLNTN95M01L736X', 'Chitarra classica.jpg', 'IMAGE', '\\x', 1900000, 'PUBBLICO')");
            stmt.execute("INSERT INTO contenuti (CF, nome, tipo, file_dati, dimensione, privacy) VALUES ('MSSLRR94M01L219X', 'Bozza segreta.pdf', 'PDF', '\\x', 78000, 'PRIVATO')");
            System.out.println("  5 contenuti fuori cartella");

            // ── Link di condivisione ──
            stmt.execute("INSERT INTO link (token, CF, id_cartella, tipo, data_scadenza, stato) " +
                "VALUES ('abc123def456', 'RSSMRA85M01A271X', 1, 'CARTELLA', '2027-12-31', 'VALIDO')");
            stmt.execute("INSERT INTO link (token, CF, id_cartella, tipo, data_scadenza, stato) " +
                "VALUES ('ghi789jkl012', 'RSSMRA85M01A271X', 3, 'CARTELLA', '2026-06-30', 'VALIDO')");
            stmt.execute("INSERT INTO link (token, CF, id_contenuto, tipo, data_scadenza, stato) " +
                "VALUES ('mno345pqr678', 'VRDLGI90M15F205X', 14, 'CONTENUTO', '2025-12-31', 'VALIDO')");
            stmt.execute("INSERT INTO link (token, CF, id_contenuto, tipo, data_scadenza, stato) " +
                "VALUES ('stu901vwx234', 'BNCSRA92M41H123X', 19, 'CONTENUTO', '2026-03-15', 'SCADUTO')");
            stmt.execute("INSERT INTO link (token, CF, id_cartella, tipo, data_scadenza, stato) " +
                "VALUES ('yz567abc890', 'GLLNTN95M01L736X', 11, 'CARTELLA', '2025-09-01', 'VALIDO')");
            stmt.execute("INSERT INTO link (token, CF, id_contenuto, tipo, data_scadenza, stato) " +
                "VALUES ('def123ghi456', 'CLNPLC91M01G573X', 31, 'CONTENUTO', '2027-01-01', 'VALIDO')");
            System.out.println("  6 link inseriti");

            // ── Utenti esterni ──
            stmt.execute("INSERT INTO utenti_esterni (nome, cognome, email, ruolo, riscontro) " +
                "VALUES ('Giovanni', 'Neri', 'giovanni.neri@email.com', 'DOCENTE', 5)");
            stmt.execute("INSERT INTO utenti_esterni (nome, cognome, email, ruolo, riscontro) " +
                "VALUES ('Elena', 'Gialli', 'elena.gialli@email.com', 'DIRETTORE', 4)");
            stmt.execute("INSERT INTO utenti_esterni (nome, cognome, email, ruolo, riscontro) " +
                "VALUES ('Francesco', 'Blu', 'francesco.blu@email.com', 'DOCENTE', 3)");
            stmt.execute("INSERT INTO utenti_esterni (nome, cognome, email, ruolo, riscontro) " +
                "VALUES ('Chiara', 'Viola', 'chiara.viola@email.com', 'STUDENTE', NULL)");
            stmt.execute("INSERT INTO utenti_esterni (nome, cognome, email, ruolo, riscontro) " +
                "VALUES ('Andrea', 'Arancioni', 'andrea.arancioni@email.com', 'ESTERNO', NULL)");
            System.out.println("  5 utenti esterni inseriti");

            // ── Segnalazioni ──
            stmt.execute("INSERT INTO segnalazioni (id_utente_esterno, id_contenuto, id_amministratore, motivo, descrizione) " +
                "VALUES (1, 1, 1, 'COPYRIGHT', 'Contenuto potenzialmente coperto da copyright senza autorizzazione')");
            stmt.execute("INSERT INTO segnalazioni (id_utente_esterno, id_contenuto, motivo, descrizione) " +
                "VALUES (2, 6, 'INAPPROPRIATO', 'Video con linguaggio inappropriato per un contesto accademico')");
            stmt.execute("INSERT INTO segnalazioni (id_utente_esterno, id_contenuto, motivo, descrizione) " +
                "VALUES (3, 14, 'PLAGIO', 'La composizione sembra plagiata da un noto brano')");
            stmt.execute("INSERT INTO segnalazioni (id_utente_esterno, id_contenuto, motivo, descrizione) " +
                "VALUES (4, 31, 'ALTRO', 'Il file sembra corrotto o non funzionante')");
            System.out.println("  4 segnalazioni inserite");

            stmt.close();
            dbManager.close();
            System.out.println("\n=== Database popolato con successo! ===");
            System.out.println("  2 amministratori, 8 studenti, 21 cartelle,");
            System.out.println("  48 contenuti, 6 link, 5 utenti esterni, 4 segnalazioni.");
        } catch (Exception e) {
            System.err.println("ERRORE: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Verifica che PostgreSQL sia in esecuzione e che il database");
            System.err.println("'portfolioafam' esista con lo schema creato.");
            System.err.println("Esegui: psql -U postgres -d portfolioafam -f src/main/resources/sql/schema.sql");
            System.exit(1);
        }
    }
}
