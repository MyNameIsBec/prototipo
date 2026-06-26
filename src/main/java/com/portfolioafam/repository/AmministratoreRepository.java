package com.portfolioafam.repository;

import com.portfolioafam.model.AmministratoreEntity;
import java.sql.*;
import java.util.Optional;

public class AmministratoreRepository {

    private final DatabaseManager dbManager;

    public AmministratoreRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<AmministratoreEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id_amministratore, email, hash_password, email2fa FROM amministratori WHERE id_amministratore = ?";
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

    public Optional<AmministratoreEntity> findByEmail(String email) throws SQLException {
        String sql = "SELECT id_amministratore, email, hash_password, email2fa FROM amministratori WHERE email = ?";
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

    public void save(AmministratoreEntity a) throws SQLException {
        String sql = "INSERT INTO amministratori (email, hash_password, email2fa) VALUES (?, ?, ?) ON CONFLICT (email) DO UPDATE SET hash_password = EXCLUDED.hash_password, email2fa = EXCLUDED.email2fa RETURNING id_amministratore";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, a.getEmail());
            stmt.setString(2, a.getHashPassword());
            stmt.setString(3, a.getEmail2fa());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    a.setIdAmministratore(rs.getLong("id_amministratore"));
                }
            }
        }
    }

    private AmministratoreEntity mapRow(ResultSet rs) throws SQLException {
        AmministratoreEntity a = new AmministratoreEntity();
        a.setIdAmministratore(rs.getLong("id_amministratore"));
        a.setEmail(rs.getString("email"));
        a.setHashPassword(rs.getString("hash_password"));
        a.setEmail2fa(rs.getString("email2fa"));
        return a;
    }
}
