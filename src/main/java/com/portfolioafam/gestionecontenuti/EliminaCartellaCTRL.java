package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.repository.CartellaRepository;
import java.sql.SQLException;

public class EliminaCartellaCTRL {
    private final CartellaRepository cartellaRepository;
    public EliminaCartellaCTRL(CartellaRepository r) { this.cartellaRepository = r; }
    public void elimina(Long id) throws SQLException { cartellaRepository.deleteById(id); }
}
