package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.repository.LinkRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;
public class GeneraLinkPortfolioCTRL {
    private final LinkRepository linkRepository;
    public GeneraLinkPortfolioCTRL(LinkRepository r) { this.linkRepository = r; }
    public LinkEntity genera(Date dataScadenza) throws SQLException {
        LinkEntity l = new LinkEntity(UUID.randomUUID().toString().replace("-",""), SessionManager.getInstance().getCf(), "PORTFOLIO", dataScadenza, "VALIDO");
        return linkRepository.save(l);
    }
}
