package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.SQLException;
import java.util.List;

public class VisualizzaContenutiCTRL {
    private final ContenutoRepository contenutoRepository;
    public VisualizzaContenutiCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public List<ContenutoEntity> getContenuti() throws SQLException {
        return contenutoRepository.findByCf(SessionManager.getInstance().getCf());
    }
}
