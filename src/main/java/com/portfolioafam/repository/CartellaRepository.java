package com.portfolioafam.repository;

import com.portfolioafam.model.CartellaEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartellaRepository {

    private final DatabaseManager dbManager;

    public CartellaRepository(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Optional<CartellaEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id_cartella, CF, nome_cartella, privacy FROM cartelle WHERE id_cartella = ?";
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

    public List<CartellaEntity> findByCf(String cf) throws SQLException {
        String sql = "SELECT id_cartella, CF, nome_cartella, privacy FROM cartelle WHERE CF = ?";
        List<CartellaEntity> list = new ArrayList<>();
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

    public CartellaEntity save(CartellaEntity c) throws SQLException {
        if (c.getIdCartella() == null) {
            String sql = "INSERT INTO cartelle (CF, nome_cartella, privacy) VALUES (?, ?, ?) RETURNING id_cartella";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setString(1, c.getCf());
                stmt.setString(2, c.getNomeCartella());
                stmt.setString(3, c.getPrivacy());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        c.setIdCartella(rs.getLong("id_cartella"));
                    }
                }
            }
        } else {
            String sql = "UPDATE cartelle SET nome_cartella = ?, privacy = ? WHERE id_cartella = ?";
            try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
                stmt.setString(1, c.getNomeCartella());
                stmt.setString(2, c.getPrivacy());
                stmt.setLong(3, c.getIdCartella());
                stmt.executeUpdate();
            }
        }
        return c;
    }

    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM cartelle WHERE id_cartella = ?";
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private CartellaEntity mapRow(ResultSet rs) throws SQLException {
        CartellaEntity c = new CartellaEntity();
        c.setIdCartella(rs.getLong("id_cartella"));
        c.setCf(rs.getString("CF"));
        c.setNomeCartella(rs.getString("nome_cartella"));
        c.setPrivacy(rs.getString("privacy"));
        return c;
    }
}
