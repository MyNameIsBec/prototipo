package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.repository.LinkRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;
public class GeneraLinkCartellaCTRL {
    private final LinkRepository linkRepository;
    public GeneraLinkCartellaCTRL(LinkRepository r) { this.linkRepository = r; }
    public LinkEntity genera(Long idCartella, Date dataScadenza) throws SQLException {
        LinkEntity l = new LinkEntity(UUID.randomUUID().toString().replace("-",""), SessionManager.getInstance().getCf(), "CARTELLA", dataScadenza, "VALIDO");
        l.setIdCartella(idCartella);
        return linkRepository.save(l);
    }
}
