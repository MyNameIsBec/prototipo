package com.portfolioafam.repository;

import com.portfolioafam.model.StudenteEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudenteRepository {

    private final DatabaseManager dbManager;

    public StudenteRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<StudenteEntity> findByCf(String cf) throws SQLException {
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo, password_temporanea, eliminazione, data_eliminazione FROM studenti WHERE CF = ? AND (eliminazione IS NULL OR eliminazione = FALSE)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<StudenteEntity> findByCfIncludingDeleted(String cf) throws SQLException {
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo, password_temporanea, eliminazione, data_eliminazione FROM studenti WHERE CF = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<StudenteEntity> findByEmail(String email) throws SQLException {
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo, password_temporanea, eliminazione, data_eliminazione FROM studenti WHERE email = ? AND (eliminazione IS NULL OR eliminazione = FALSE)";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public void save(StudenteEntity s) throws SQLException {
        String sql = "INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo, password_temporanea, eliminazione, data_eliminazione) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (CF) DO UPDATE SET nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, email = EXCLUDED.email, hash_password = EXCLUDED.hash_password, telefono = EXCLUDED.telefono, email2fa = EXCLUDED.email2fa, immagine_profilo = EXCLUDED.immagine_profilo, dati_accademici = EXCLUDED.dati_accademici, visibilita_profilo = EXCLUDED.visibilita_profilo, password_temporanea = EXCLUDED.password_temporanea, eliminazione = EXCLUDED.eliminazione, data_eliminazione = EXCLUDED.data_eliminazione";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, s.getCf());
            stmt.setString(2, s.getNome());
            stmt.setString(3, s.getCognome());
            stmt.setString(4, s.getEmail());
            stmt.setString(5, s.getHashPassword());
            stmt.setString(6, s.getTelefono());
            stmt.setString(7, s.getEmail2fa());
            stmt.setBytes(8, s.getImmagineProfilo());
            stmt.setString(9, s.getDatiAccademici());
            stmt.setString(10, s.getVisibilitaProfilo());
            stmt.setBoolean(11, s.isPasswordTemporanea());
            stmt.setBoolean(12, s.isEliminazione());
            stmt.setTimestamp(13, s.getDataEliminazione());
            stmt.executeUpdate();
        }
    }

    public void softDeleteByCf(String cf) throws SQLException {
        String sql = "UPDATE studenti SET eliminazione = TRUE, data_eliminazione = NOW() WHERE CF = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            stmt.executeUpdate();
        }
    }

    public void ripristinaByCf(String cf) throws SQLException {
        String sql = "UPDATE studenti SET eliminazione = FALSE, data_eliminazione = NULL WHERE CF = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            stmt.executeUpdate();
        }
    }

    public Optional<StudenteEntity> findDeletedByCf(String cf) throws SQLException {
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo, password_temporanea, eliminazione, data_eliminazione FROM studenti WHERE CF = ? AND eliminazione = TRUE";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public List<StudenteEntity> findByVisibilita(String visibilita) throws SQLException {
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo FROM studenti WHERE visibilita_profilo = ?";
        List<StudenteEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, visibilita);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<StudenteEntity> searchByNome(String nome) throws SQLException {
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo FROM studenti WHERE (nome ILIKE ? OR cognome ILIKE ?) AND visibilita_profilo = 'PUBBLICO'";
        List<StudenteEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            String pattern = "%" + nome + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<StudenteEntity> searchByNomePublic(String nome) throws SQLException {
        if (nome == null || nome.trim().isEmpty()) return new ArrayList<>();
        String[] tokens = nome.trim().split("\\s+");
        StringBuilder sql = new StringBuilder(
            "SELECT CF, nome, cognome, dati_accademici FROM studenti WHERE visibilita_profilo = 'PUBBLICO' AND (eliminazione IS NULL OR eliminazione = FALSE)");
        for (int i = 0; i < tokens.length; i++) {
            sql.append(" AND (nome ILIKE ? OR cognome ILIKE ?)");
        }
        List<StudenteEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql.toString())) {
            int idx = 1;
            for (String t : tokens) {
                String p = "%" + t + "%";
                stmt.setString(idx++, p);
                stmt.setString(idx++, p);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StudenteEntity s = new StudenteEntity();
                    s.setCf(rs.getString("CF"));
                    s.setNome(rs.getString("nome"));
                    s.setCognome(rs.getString("cognome"));
                    s.setDatiAccademici(rs.getString("dati_accademici"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<StudenteEntity> searchByNomeAndStrumentoPublic(String nome, String strumento) throws SQLException {
        String sql = "SELECT CF, nome, cognome, dati_accademici FROM studenti WHERE (nome ILIKE ? OR cognome ILIKE ?) AND visibilita_profilo = 'PUBBLICO'";
        if (strumento != null && !strumento.trim().isEmpty()) {
            sql += " AND dati_accademici ILIKE ?";
        }
        List<StudenteEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            String pattern = "%" + nome + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            if (strumento != null && !strumento.trim().isEmpty()) {
                stmt.setString(3, "%" + strumento + "%");
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StudenteEntity s = new StudenteEntity();
                    s.setCf(rs.getString("CF"));
                    s.setNome(rs.getString("nome"));
                    s.setCognome(rs.getString("cognome"));
                    s.setDatiAccademici(rs.getString("dati_accademici"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<StudenteEntity> searchByNomeAndFiltriPublic(String nome, String corso, String anno, boolean laureato) throws SQLException {
        String[] tokens = (nome != null) ? nome.trim().split("\\s+") : new String[0];
        boolean hasNome = tokens.length > 0 && !tokens[0].isEmpty();
        StringBuilder sql = new StringBuilder(
            "SELECT CF, nome, cognome, dati_accademici FROM studenti WHERE visibilita_profilo = 'PUBBLICO' AND (eliminazione IS NULL OR eliminazione = FALSE)");
        if (hasNome) {
            for (int i = 0; i < tokens.length; i++) {
                sql.append(" AND (nome ILIKE ? OR cognome ILIKE ?)");
            }
        }
        if (corso != null && !corso.trim().isEmpty()) {
            sql.append(" AND dati_accademici ILIKE ?");
        }
        if (anno != null && !anno.trim().isEmpty()) {
            sql.append(" AND dati_accademici ILIKE ?");
        }
        if (laureato) {
            sql.append(" AND dati_accademici ILIKE ?");
        }
        List<StudenteEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql.toString())) {
            int idx = 1;
            if (hasNome) {
                for (String t : tokens) {
                    String p = "%" + t + "%";
                    stmt.setString(idx++, p);
                    stmt.setString(idx++, p);
                }
            }
            if (corso != null && !corso.trim().isEmpty()) {
                stmt.setString(idx++, "%" + corso + "%");
            }
            if (anno != null && !anno.trim().isEmpty()) {
                stmt.setString(idx++, "%" + anno + "%");
            }
            if (laureato) {
                stmt.setString(idx, "%laureato%");
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StudenteEntity s = new StudenteEntity();
                    s.setCf(rs.getString("CF"));
                    s.setNome(rs.getString("nome"));
                    s.setCognome(rs.getString("cognome"));
                    s.setDatiAccademici(rs.getString("dati_accademici"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    public List<String> getCorsiDistinct() throws SQLException {
        String sql = "SELECT DISTINCT dati_accademici FROM studenti WHERE visibilita_profilo = 'PUBBLICO' AND dati_accademici IS NOT NULL";
        List<String> corsi = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String da = rs.getString("dati_accademici");
                if (da != null) {
                    String corso = da.split(" - ")[0].trim();
                    if (!corso.isEmpty() && !corsi.contains(corso)) corsi.add(corso);
                }
            }
        }
        return corsi;
    }

    public List<String> getAnniDistinct() throws SQLException {
        String sql = "SELECT DISTINCT dati_accademici FROM studenti WHERE visibilita_profilo = 'PUBBLICO' AND dati_accademici IS NOT NULL";
        List<String> anni = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String da = rs.getString("dati_accademici");
                if (da != null && da.contains("A.A. ")) {
                    String anno = da.substring(da.indexOf("A.A. ") + 5).trim();
                    if (!anno.isEmpty() && !anni.contains(anno)) anni.add(anno);
                }
            }
        }
        return anni;
    }

    private StudenteEntity mapRow(ResultSet rs) throws SQLException {
        StudenteEntity s = new StudenteEntity();
        s.setCf(rs.getString("CF"));
        s.setNome(rs.getString("nome"));
        s.setCognome(rs.getString("cognome"));
        s.setEmail(rs.getString("email"));
        s.setHashPassword(rs.getString("hash_password"));
        s.setTelefono(rs.getString("telefono"));
        s.setEmail2fa(rs.getString("email2fa"));
        s.setImmagineProfilo(rs.getBytes("immagine_profilo"));
        s.setDatiAccademici(rs.getString("dati_accademici"));
        s.setVisibilitaProfilo(rs.getString("visibilita_profilo"));
        s.setPasswordTemporanea(rs.getBoolean("password_temporanea"));
        try { s.setEliminazione(rs.getBoolean("eliminazione")); } catch (SQLException e) { }
        try { s.setDataEliminazione(rs.getTimestamp("data_eliminazione")); } catch (SQLException e) { }
        return s;
    }
}
