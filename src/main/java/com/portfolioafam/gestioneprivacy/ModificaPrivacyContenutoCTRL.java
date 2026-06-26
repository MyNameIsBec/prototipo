package com.portfolioafam.gestioneprivacy;
import com.portfolioafam.repository.ContenutoRepository;
import java.sql.SQLException;
public class ModificaPrivacyContenutoCTRL {
    private final ContenutoRepository contenutoRepository;
    public ModificaPrivacyContenutoCTRL(ContenutoRepository r) { this.contenutoRepository = r; }
    public void modificaPrivacy(Long id, String privacy) throws SQLException {
        contenutoRepository.findById(id).ifPresent(c -> { c.setPrivacy(privacy); try { contenutoRepository.save(c); } catch (SQLException e) { throw new RuntimeException(e); } });
    }
}
