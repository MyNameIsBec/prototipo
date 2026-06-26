package com.portfolioafam.model;

import java.sql.Timestamp;

public class SegnalazioneEntity {

    private Long idSegnalazione;
    private Long idUtenteEsterno;
    private Long idContenuto;
    private Long idAmministratore;
    private String motivo;
    private String descrizione;
    private Timestamp data;

    public SegnalazioneEntity() {
    }

    public SegnalazioneEntity(Long idUtenteEsterno, Long idContenuto, String motivo) {
        this.idUtenteEsterno = idUtenteEsterno;
        this.idContenuto = idContenuto;
        this.motivo = motivo;
    }

    public Long getIdSegnalazione() { return idSegnalazione; }
    public void setIdSegnalazione(Long idSegnalazione) { this.idSegnalazione = idSegnalazione; }

    public Long getIdUtenteEsterno() { return idUtenteEsterno; }
    public void setIdUtenteEsterno(Long idUtenteEsterno) { this.idUtenteEsterno = idUtenteEsterno; }

    public Long getIdContenuto() { return idContenuto; }
    public void setIdContenuto(Long idContenuto) { this.idContenuto = idContenuto; }

    public Long getIdAmministratore() { return idAmministratore; }
    public void setIdAmministratore(Long idAmministratore) { this.idAmministratore = idAmministratore; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Timestamp getData() { return data; }
    public void setData(Timestamp data) { this.data = data; }

    @Override
    public String toString() {
        return "SegnalazioneEntity{id=" + idSegnalazione + ", motivo='" + motivo + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SegnalazioneEntity)) return false;
        return idSegnalazione != null && idSegnalazione.equals(((SegnalazioneEntity) o).idSegnalazione);
    }

    @Override
    public int hashCode() {
        return idSegnalazione != null ? idSegnalazione.hashCode() : 0;
    }
}
