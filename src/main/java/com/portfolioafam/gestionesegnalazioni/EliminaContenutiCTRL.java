package com.portfolioafam.gestionesegnalazioni;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;
public class EliminaContenutiCTRL {
    private final ContenutoRepository contenutoRepository;
    public EliminaContenutiCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public void elimina(Long idContenuto) throws SQLException { contenutoRepository.deleteById(idContenuto); }
}
