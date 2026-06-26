package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.SQLException;
import java.util.List;

public class RicercaDocumentoCTRL {
    private final ContenutoRepository contenutoRepository;
    public RicercaDocumentoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public List<ContenutoEntity> cerca(String nome) throws SQLException {
        return contenutoRepository.searchByNome(SessionManager.getInstance().getCf(), nome);
    }
}
