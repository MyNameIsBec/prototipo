package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;

public class DuplicaContenutoCTRL {
    private final ContenutoRepository contenutoRepository;
    public DuplicaContenutoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public void duplica(Long idContenuto, Long idCartellaDestinazione) throws SQLException {
        contenutoRepository.findById(idContenuto).ifPresent(c -> {
            ContenutoEntity copia = new ContenutoEntity(c.getCf(), c.getNome() + " (copia)", c.getTipo(), c.getFileDati(), c.getDimensione(), c.getPrivacy());
            copia.setIdCartella(idCartellaDestinazione);
            try { contenutoRepository.save(copia); } catch (SQLException e) { throw new RuntimeException(e); }
        });
    }
}
