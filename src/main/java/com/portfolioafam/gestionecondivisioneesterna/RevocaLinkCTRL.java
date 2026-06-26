package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.repository.LinkRepository;
import java.sql.SQLException;
public class RevocaLinkCTRL {
    private final LinkRepository linkRepository;
    public RevocaLinkCTRL(LinkRepository r) { this.linkRepository = r; }
    public void revoca(Long idLink) throws SQLException { linkRepository.updateStato(idLink, "REVOCATO"); }
}
