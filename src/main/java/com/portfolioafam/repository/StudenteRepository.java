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
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo FROM studenti WHERE CF = ?";
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
        String sql = "SELECT CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo FROM studenti WHERE email = ?";
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
        String sql = "INSERT INTO studenti (CF, nome, cognome, email, hash_password, telefono, email2fa, immagine_profilo, dati_accademici, visibilita_profilo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (CF) DO UPDATE SET nome = EXCLUDED.nome, cognome = EXCLUDED.cognome, email = EXCLUDED.email, hash_password = EXCLUDED.hash_password, telefono = EXCLUDED.telefono, email2fa = EXCLUDED.email2fa, immagine_profilo = EXCLUDED.immagine_profilo, dati_accademici = EXCLUDED.dati_accademici, visibilita_profilo = EXCLUDED.visibilita_profilo";
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
            stmt.executeUpdate();
        }
    }

    public void deleteByCf(String cf) throws SQLException {
        String sql = "DELETE FROM studenti WHERE CF = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            stmt.executeUpdate();
        }
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
        String sql = "SELECT CF, nome, cognome, dati_accademici FROM studenti WHERE (nome ILIKE ? OR cognome ILIKE ?) AND visibilita_profilo = 'PUBBLICO'";
        List<StudenteEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            String pattern = "%" + nome + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
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
        return s;
    }
}
