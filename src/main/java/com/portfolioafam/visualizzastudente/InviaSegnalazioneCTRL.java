package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.SegnalazioneEntity;
import com.portfolioafam.repository.SegnalazioneRepository;
import java.sql.SQLException;
public class InviaSegnalazioneCTRL {
    private final SegnalazioneRepository segnalazioneRepository;
    public InviaSegnalazioneCTRL(SegnalazioneRepository r) { this.segnalazioneRepository = r; }
    public SegnalazioneEntity invia(Long idUtenteEsterno, Long idContenuto, String motivo, String descrizione) throws SQLException {
        SegnalazioneEntity s = new SegnalazioneEntity(idUtenteEsterno, idContenuto, motivo);
        s.setDescrizione(descrizione);
        return segnalazioneRepository.save(s);
    }
}
