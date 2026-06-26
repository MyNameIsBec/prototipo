package com.portfolioafam.gestioneprivacy;
import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.CartellaRepository;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;
public class ModificaPrivacyCartellaCTRL {
    private final CartellaRepository cartellaRepository;
    private final ContenutoRepository contenutoRepository;
    public ModificaPrivacyCartellaCTRL(CartellaRepository cr, ContenutoRepository cor) { this.cartellaRepository = cr; this.contenutoRepository = cor; }
    public void modificaPrivacy(Long idCartella, String privacy) throws SQLException {
        cartellaRepository.findById(idCartella).ifPresent(c -> { c.setPrivacy(privacy); try { cartellaRepository.save(c); } catch (SQLException e) { throw new RuntimeException(e); } });
        for (ContenutoEntity cont : contenutoRepository.findByCartella(idCartella)) { cont.setPrivacy(privacy); contenutoRepository.save(cont); }
    }
}
