package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.repository.LinkRepository;
import java.sql.SQLException;
import java.util.Optional;
public class VisualizzaRiscontriCTRL {
    private final LinkRepository linkRepository;
    public VisualizzaRiscontriCTRL(LinkRepository r) { this.linkRepository = r; }
    public Optional<LinkEntity> getLink(Long id) throws SQLException { return linkRepository.findById(id); }
}
