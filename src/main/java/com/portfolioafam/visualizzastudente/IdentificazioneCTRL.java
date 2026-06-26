package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.UtenteEsternoEntity;
import com.portfolioafam.repository.UtenteEsternoRepository;
import java.sql.SQLException;
public class IdentificazioneCTRL {
    private final UtenteEsternoRepository utenteEsternoRepository;
    public IdentificazioneCTRL(UtenteEsternoRepository r) { this.utenteEsternoRepository = r; }
    public UtenteEsternoEntity identifica(String nome, String cognome, String email, String ruolo) throws SQLException {
        UtenteEsternoEntity u = new UtenteEsternoEntity(nome, cognome, email, ruolo);
        return utenteEsternoRepository.save(u);
    }
}
