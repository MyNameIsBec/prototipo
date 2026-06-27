package com.portfolioafam.util;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.model.AmministratoreEntity;

public class SessionManager {

    private static SessionManager instance;

    private String ruolo;
    private StudenteEntity studente;
    private AmministratoreEntity amministratore;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void avviaSessioneStudente(StudenteEntity s) {
        this.studente = s;
        this.amministratore = null;
        this.ruolo = "STUDENTE";
    }

    public void avviaSessioneAmministratore(AmministratoreEntity a) {
        this.amministratore = a;
        this.studente = null;
        this.ruolo = "AMMINISTRATORE";
    }

    public void terminaSessione() {
        this.studente = null;
        this.amministratore = null;
        this.ruolo = null;
    }

    public boolean isAutenticato() {
        return ruolo != null;
    }

    public boolean isStudente() {
        return "STUDENTE".equals(ruolo);
    }

    public boolean isAmministratore() {
        return "AMMINISTRATORE".equals(ruolo);
    }

    public String getRuolo() {
        return ruolo;
    }

    public StudenteEntity getStudente() {
        return studente;
    }

    public String getCf() {
        return studente != null ? studente.getCf() : null;
    }

    public AmministratoreEntity getAmministratore() {
        return amministratore;
    }

    public Long getIdAmministratore() {
        return amministratore != null ? amministratore.getIdAmministratore() : null;
    }

    private Long utenteEsternoId;

    public void setUtenteEsternoId(Long id) { this.utenteEsternoId = id; }
    public Long getUtenteEsternoId() { return utenteEsternoId; }

    public static void reset() {
        instance = null;
    }
}
