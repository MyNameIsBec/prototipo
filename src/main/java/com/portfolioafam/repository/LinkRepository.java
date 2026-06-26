package com.portfolioafam.repository;

import com.portfolioafam.model.LinkEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinkRepository {

    private final DatabaseManager dbManager;

    public LinkRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<LinkEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id_link, token, CF, id_cartella, id_contenuto, tipo, data_scadenza, stato, data_creazione FROM link WHERE id_link = ?";
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

    public Optional<LinkEntity> findByToken(String token) throws SQLException {
        String sql = "SELECT id_link, token, CF, id_cartella, id_contenuto, tipo, data_scadenza, stato, data_creazione FROM link WHERE token = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public List<LinkEntity> findByCf(String cf) throws SQLException {
        String sql = "SELECT id_link, token, CF, id_cartella, id_contenuto, tipo, data_scadenza, stato, data_creazione FROM link WHERE CF = ?";
        List<LinkEntity> list = new ArrayList<>();
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

    public LinkEntity save(LinkEntity l) throws SQLException {
        if (l.getIdLink() == null) {
            String sql = "INSERT INTO link (token, CF, id_cartella, id_contenuto, tipo, data_scadenza, stato) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_link, data_creazione";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setString(1, l.getToken());
                stmt.setString(2, l.getCf());
                if (l.getIdCartella() != null) stmt.setLong(3, l.getIdCartella());
                else stmt.setNull(3, Types.BIGINT);
                if (l.getIdContenuto() != null) stmt.setLong(4, l.getIdContenuto());
                else stmt.setNull(4, Types.BIGINT);
                stmt.setString(5, l.getTipo());
                stmt.setDate(6, l.getDataScadenza());
                stmt.setString(7, l.getStato());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        l.setIdLink(rs.getLong("id_link"));
                        l.setDataCreazione(rs.getTimestamp("data_creazione"));
                    }
                }
            }
        } else {
            String sql = "UPDATE link SET data_scadenza = ?, stato = ? WHERE id_link = ?";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setDate(1, l.getDataScadenza());
                stmt.setString(2, l.getStato());
                stmt.setLong(3, l.getIdLink());
                stmt.executeUpdate();
            }
        }
        return l;
    }

    public void updateStato(Long idLink, String stato) throws SQLException {
        String sql = "UPDATE link SET stato = ? WHERE id_link = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, stato);
            stmt.setLong(2, idLink);
            stmt.executeUpdate();
        }
    }

    private LinkEntity mapRow(ResultSet rs) throws SQLException {
        LinkEntity l = new LinkEntity();
        l.setIdLink(rs.getLong("id_link"));
        l.setToken(rs.getString("token"));
        l.setCf(rs.getString("CF"));
        long idCartella = rs.getLong("id_cartella");
        l.setIdCartella(rs.wasNull() ? null : idCartella);
        long idContenuto = rs.getLong("id_contenuto");
        l.setIdContenuto(rs.wasNull() ? null : idContenuto);
        l.setTipo(rs.getString("tipo"));
        l.setDataScadenza(rs.getDate("data_scadenza"));
        l.setStato(rs.getString("stato"));
        l.setDataCreazione(rs.getTimestamp("data_creazione"));
        return l;
    }
}
