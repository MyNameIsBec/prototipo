package com.portfolioafam.gestionesegnalazioni;
import com.portfolioafam.model.SegnalazioneEntity;
import com.portfolioafam.repository.SegnalazioneRepository;
import java.sql.SQLException;
import java.util.List;
public class SegnalazioniCTRL {
    private final SegnalazioneRepository segnalazioneRepository;
    public SegnalazioniCTRL(SegnalazioneRepository r) { this.segnalazioneRepository = r; }
    public List<SegnalazioneEntity> getSegnalazioni() throws SQLException { return segnalazioneRepository.findAll(); }
}
