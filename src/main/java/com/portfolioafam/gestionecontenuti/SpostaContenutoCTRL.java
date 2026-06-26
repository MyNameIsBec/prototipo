package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;

public class SpostaContenutoCTRL {
    private final ContenutoRepository contenutoRepository;
    public SpostaContenutoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public void sposta(Long idContenuto, Long idCartellaDestinazione) throws SQLException {
        contenutoRepository.findById(idContenuto).ifPresent(c -> { c.setIdCartella(idCartellaDestinazione); try { contenutoRepository.save(c); } catch (SQLException e) { throw new RuntimeException(e); } });
    }
}
