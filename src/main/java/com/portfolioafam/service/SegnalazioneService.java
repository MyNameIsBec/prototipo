package com.portfolioafam.service;
import com.portfolioafam.model.SegnalazioneEntity;
import com.portfolioafam.repository.SegnalazioneRepository;
import java.sql.SQLException;
import java.util.List;
public class SegnalazioneService {
    private final SegnalazioneRepository segnalazioneRepository;
    public SegnalazioneService(SegnalazioneRepository r) { this.segnalazioneRepository = r; }
    public List<SegnalazioneEntity> getSegnalazioni() throws SQLException { return segnalazioneRepository.findAll(); }
    public SegnalazioneEntity creaSegnalazione(SegnalazioneEntity s) throws SQLException { return segnalazioneRepository.save(s); }
}
