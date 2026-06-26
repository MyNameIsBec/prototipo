package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;

public class EliminaContenutoCTRL {
    private final ContenutoRepository contenutoRepository;
    public EliminaContenutoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public void elimina(Long id) throws SQLException { contenutoRepository.deleteById(id); }
}
