package com.portfolioafam.repository;

import com.portfolioafam.model.UtenteEsternoEntity;
import java.sql.*;
import java.util.Optional;

public class UtenteEsternoRepository {

    private final DatabaseManager dbManager;

    public UtenteEsternoRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<UtenteEsternoEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id_utente_esterno, nome, cognome, email, ruolo, riscontro FROM utenti_esterni WHERE id_utente_esterno = ?";
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

    public UtenteEsternoEntity save(UtenteEsternoEntity u) throws SQLException {
        if (u.getIdUtenteEsterno() == null) {
            String sql = "INSERT INTO utenti_esterni (nome, cognome, email, ruolo, riscontro) VALUES (?, ?, ?, ?, ?) RETURNING id_utente_esterno";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getCognome());
                stmt.setString(3, u.getEmail());
                stmt.setString(4, u.getRuolo());
                if (u.getRiscontro() != null) stmt.setInt(5, u.getRiscontro());
                else stmt.setNull(5, Types.INTEGER);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        u.setIdUtenteEsterno(rs.getLong("id_utente_esterno"));
                    }
                }
            }
        } else {
            String sql = "UPDATE utenti_esterni SET nome = ?, cognome = ?, email = ?, ruolo = ?, riscontro = ? WHERE id_utente_esterno = ?";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getCognome());
                stmt.setString(3, u.getEmail());
                stmt.setString(4, u.getRuolo());
                if (u.getRiscontro() != null) stmt.setInt(5, u.getRiscontro());
                else stmt.setNull(5, Types.INTEGER);
                stmt.setLong(6, u.getIdUtenteEsterno());
                stmt.executeUpdate();
            }
        }
        return u;
    }

    private UtenteEsternoEntity mapRow(ResultSet rs) throws SQLException {
        UtenteEsternoEntity u = new UtenteEsternoEntity();
        u.setIdUtenteEsterno(rs.getLong("id_utente_esterno"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setEmail(rs.getString("email"));
        u.setRuolo(rs.getString("ruolo"));
        int riscontro = rs.getInt("riscontro");
        u.setRiscontro(rs.wasNull() ? null : riscontro);
        return u;
    }
}
