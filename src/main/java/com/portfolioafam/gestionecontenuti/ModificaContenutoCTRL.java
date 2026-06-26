package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;

public class ModificaContenutoCTRL {
    private final ContenutoRepository contenutoRepository;
    public ModificaContenutoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public void modifica(Long id, String nome, String tipo, String privacy) throws SQLException {
        contenutoRepository.findById(id).ifPresent(c -> {
            if (nome != null) c.setNome(nome);
            if (tipo != null) c.setTipo(tipo);
            if (privacy != null) c.setPrivacy(privacy);
            try { contenutoRepository.save(c); } catch (SQLException e) { throw new RuntimeException(e); }
        });
    }
}
