package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.repository.LinkRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;
public class GeneraLinkContenutoCTRL {
    private final LinkRepository linkRepository;
    public GeneraLinkContenutoCTRL(LinkRepository r) { this.linkRepository = r; }
    public LinkEntity genera(Long idContenuto, Date dataScadenza) throws SQLException {
        LinkEntity l = new LinkEntity(UUID.randomUUID().toString().replace("-",""), SessionManager.getInstance().getCf(), "CONTENUTO", dataScadenza, "VALIDO");
        l.setIdContenuto(idContenuto);
        return linkRepository.save(l);
    }
}
