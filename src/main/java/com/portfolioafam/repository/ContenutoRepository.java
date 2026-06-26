package com.portfolioafam.repository;

import com.portfolioafam.model.ContenutoEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContenutoRepository {

    private final DatabaseManager dbManager;

    public ContenutoRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<ContenutoEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id_contenuto, CF, id_cartella, nome, tipo, file_dati, dimensione, data_caricamento, privacy FROM contenuti WHERE id_contenuto = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public List<ContenutoEntity> findByCf(String cf) throws SQLException {
        String sql = "SELECT id_contenuto, CF, id_cartella, nome, tipo, file_dati, dimensione, data_caricamento, privacy FROM contenuti WHERE CF = ?";
        List<ContenutoEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<ContenutoEntity> findByCartella(Long idCartella) throws SQLException {
        String sql = "SELECT id_contenuto, CF, id_cartella, nome, tipo, file_dati, dimensione, data_caricamento, privacy FROM contenuti WHERE id_cartella = ?";
        List<ContenutoEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, idCartella);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<ContenutoEntity> searchByNome(String cf, String nome) throws SQLException {
        String sql = "SELECT id_contenuto, CF, id_cartella, nome, tipo, file_dati, dimensione, data_caricamento, privacy FROM contenuti WHERE CF = ? AND nome ILIKE ?";
        List<ContenutoEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, cf);
            stmt.setString(2, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public ContenutoEntity save(ContenutoEntity c) throws SQLException {
        if (c.getIdContenuto() == null) {
            String sql = "INSERT INTO contenuti (CF, id_cartella, nome, tipo, file_dati, dimensione, privacy) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_contenuto, data_caricamento";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setString(1, c.getCf());
                if (c.getIdCartella() != null) stmt.setLong(2, c.getIdCartella());
                else stmt.setNull(2, Types.BIGINT);
                stmt.setString(3, c.getNome());
                stmt.setString(4, c.getTipo());
                stmt.setBytes(5, c.getFileDati());
                stmt.setLong(6, c.getDimensione());
                stmt.setString(7, c.getPrivacy());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        c.setIdContenuto(rs.getLong("id_contenuto"));
                        c.setDataCaricamento(rs.getTimestamp("data_caricamento"));
                    }
                }
            }
        } else {
            String sql = "UPDATE contenuti SET id_cartella = ?, nome = ?, tipo = ?, privacy = ? WHERE id_contenuto = ?";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                if (c.getIdCartella() != null) stmt.setLong(1, c.getIdCartella());
                else stmt.setNull(1, Types.BIGINT);
                stmt.setString(2, c.getNome());
                stmt.setString(3, c.getTipo());
                stmt.setString(4, c.getPrivacy());
                stmt.setLong(5, c.getIdContenuto());
                stmt.executeUpdate();
            }
        }
        return c;
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM contenuti WHERE id_contenuto = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private ContenutoEntity mapRow(ResultSet rs) throws SQLException {
        ContenutoEntity c = new ContenutoEntity();
        c.setIdContenuto(rs.getLong("id_contenuto"));
        c.setCf(rs.getString("CF"));
        long idCartella = rs.getLong("id_cartella");
        c.setIdCartella(rs.wasNull() ? null : idCartella);
        c.setNome(rs.getString("nome"));
        c.setTipo(rs.getString("tipo"));
        c.setFileDati(rs.getBytes("file_dati"));
        c.setDimensione(rs.getLong("dimensione"));
        c.setDataCaricamento(rs.getTimestamp("data_caricamento"));
        c.setPrivacy(rs.getString("privacy"));
        return c;
    }
}
