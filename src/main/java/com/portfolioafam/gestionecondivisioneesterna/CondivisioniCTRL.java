package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.repository.LinkRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.SQLException;
import java.util.List;
public class CondivisioniCTRL {
    private final LinkRepository linkRepository;
    public CondivisioniCTRL(LinkRepository r) { this.linkRepository = r; }
    public List<LinkEntity> getLinks() throws SQLException { return linkRepository.findByCf(SessionManager.getInstance().getCf()); }
}
