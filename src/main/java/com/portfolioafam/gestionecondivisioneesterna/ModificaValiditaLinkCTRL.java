package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.repository.LinkRepository;
import java.sql.Date;
import java.sql.SQLException;
public class ModificaValiditaLinkCTRL {
    private final LinkRepository linkRepository;
    public ModificaValiditaLinkCTRL(LinkRepository r) { this.linkRepository = r; }
    public void modificaScadenza(Long idLink, Date nuovaScadenza) throws SQLException {
        linkRepository.findById(idLink).ifPresent(l -> { l.setDataScadenza(nuovaScadenza); try { linkRepository.save(l); } catch (SQLException e) { throw new RuntimeException(e); } });
    }
}
