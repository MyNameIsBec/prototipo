package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.model.CartellaEntity;
import com.portfolioafam.repository.CartellaRepository;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ScaricaCartellaCTRL {
    private final CartellaRepository cartellaRepository;
    private final ContenutoRepository contenutoRepository;
    public ScaricaCartellaCTRL(CartellaRepository cr, ContenutoRepository cor) { this.cartellaRepository = cr; this.contenutoRepository = cor; }
    public Optional<CartellaEntity> getCartella(Long id) throws SQLException { return cartellaRepository.findById(id); }
    public List<ContenutoEntity> getContenutiCartella(Long idCartella) throws SQLException { return contenutoRepository.findByCartella(idCartella); }
}
