package com.portfolioafam.model;

import java.sql.Date;
import java.sql.Timestamp;

public class LinkEntity {

    private Long idLink;
    private String token;
    private String cf;
    private Long idCartella;
    private Long idContenuto;
    private String tipo;
    private Date dataScadenza;
    private String stato;
    private Timestamp dataCreazione;

    public LinkEntity() {
    }

    public LinkEntity(String token, String cf, String tipo, Date dataScadenza, String stato) {
        this.token = token;
        this.cf = cf;
        this.tipo = tipo;
        this.dataScadenza = dataScadenza;
        this.stato = stato;
    }

    public Long getIdLink() { return idLink; }
    public void setIdLink(Long idLink) { this.idLink = idLink; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getCf() { return cf; }
    public void setCf(String cf) { this.cf = cf; }

    public Long getIdCartella() { return idCartella; }
    public void setIdCartella(Long idCartella) { this.idCartella = idCartella; }

    public Long getIdContenuto() { return idContenuto; }
    public void setIdContenuto(Long idContenuto) { this.idContenuto = idContenuto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Date getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(Date dataScadenza) { this.dataScadenza = dataScadenza; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    public Timestamp getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(Timestamp dataCreazione) { this.dataCreazione = dataCreazione; }

    @Override
    public String toString() {
        return "LinkEntity{id=" + idLink + ", token='" + token + "', tipo='" + tipo + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkEntity)) return false;
        return idLink != null && idLink.equals(((LinkEntity) o).idLink);
    }

    @Override
    public int hashCode() {
        return idLink != null ? idLink.hashCode() : 0;
    }
}
