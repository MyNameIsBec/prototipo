package com.portfolioafam.repository;

import com.portfolioafam.model.SegnalazioneEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SegnalazioneRepository {

    private final DatabaseManager dbManager;

    public SegnalazioneRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<SegnalazioneEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id_segnalazione, id_utente_esterno, id_contenuto, id_amministratore, motivo, descrizione, data FROM segnalazioni WHERE id_segnalazione = ?";
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

    public List<SegnalazioneEntity> findAll() throws SQLException {
        String sql = "SELECT id_segnalazione, id_utente_esterno, id_contenuto, id_amministratore, motivo, descrizione, data FROM segnalazioni ORDER BY data DESC";
        List<SegnalazioneEntity> list = new ArrayList<>();
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public SegnalazioneEntity save(SegnalazioneEntity s) throws SQLException {
        if (s.getIdSegnalazione() == null) {
            String sql = "INSERT INTO segnalazioni (id_utente_esterno, id_contenuto, motivo, descrizione) VALUES (?, ?, ?, ?) RETURNING id_segnalazione, data";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setLong(1, s.getIdUtenteEsterno());
                stmt.setLong(2, s.getIdContenuto());
                stmt.setString(3, s.getMotivo());
                stmt.setString(4, s.getDescrizione());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        s.setIdSegnalazione(rs.getLong("id_segnalazione"));
                        s.setData(rs.getTimestamp("data"));
                    }
                }
            }
        } else {
            String sql = "UPDATE segnalazioni SET id_amministratore = ?, motivo = ?, descrizione = ? WHERE id_segnalazione = ?";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                if (s.getIdAmministratore() != null) stmt.setLong(1, s.getIdAmministratore());
                else stmt.setNull(1, Types.BIGINT);
                stmt.setString(2, s.getMotivo());
                stmt.setString(3, s.getDescrizione());
                stmt.setLong(4, s.getIdSegnalazione());
                stmt.executeUpdate();
            }
        }
        return s;
    }

    private SegnalazioneEntity mapRow(ResultSet rs) throws SQLException {
        SegnalazioneEntity s = new SegnalazioneEntity();
        s.setIdSegnalazione(rs.getLong("id_segnalazione"));
        s.setIdUtenteEsterno(rs.getLong("id_utente_esterno"));
        s.setIdContenuto(rs.getLong("id_contenuto"));
        long idAdmin = rs.getLong("id_amministratore");
        s.setIdAmministratore(rs.wasNull() ? null : idAdmin);
        s.setMotivo(rs.getString("motivo"));
        s.setDescrizione(rs.getString("descrizione"));
        s.setData(rs.getTimestamp("data"));
        return s;
    }
}
