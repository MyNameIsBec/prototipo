package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.CartellaEntity;
import com.portfolioafam.repository.CartellaRepository;
import java.sql.SQLException;

public class ModificaCartellaCTRL {
    private final CartellaRepository cartellaRepository;
    public ModificaCartellaCTRL(CartellaRepository r) { this.cartellaRepository = r; }
    public void modifica(Long id, String nomeCartella, String privacy) throws SQLException {
        cartellaRepository.findById(id).ifPresent(c -> {
            if (nomeCartella != null) c.setNomeCartella(nomeCartella);
            if (privacy != null) c.setPrivacy(privacy);
            try { cartellaRepository.save(c); } catch (SQLException e) { throw new RuntimeException(e); }
        });
    }
}
