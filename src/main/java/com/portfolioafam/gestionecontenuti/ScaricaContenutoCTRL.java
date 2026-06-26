package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;
import java.util.Optional;

public class ScaricaContenutoCTRL {
    private final ContenutoRepository contenutoRepository;
    public ScaricaContenutoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public Optional<ContenutoEntity> getContenuto(Long id) throws SQLException { return contenutoRepository.findById(id); }
    public byte[] scarica(Long id) throws SQLException {
        return contenutoRepository.findById(id).map(ContenutoEntity::getFileDati).orElse(null);
    }
}
