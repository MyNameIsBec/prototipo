package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.CartellaEntity;
import com.portfolioafam.repository.CartellaRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.SQLException;

public class CreaCartellaCTRL {
    private final CartellaRepository cartellaRepository;
    public CreaCartellaCTRL(CartellaRepository r) { this.cartellaRepository = r; }
    public CartellaEntity crea(String nomeCartella, String privacy) throws SQLException {
        CartellaEntity c = new CartellaEntity(SessionManager.getInstance().getCf(), nomeCartella, privacy);
        return cartellaRepository.save(c);
    }
}
