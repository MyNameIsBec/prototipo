package com.portfolioafam.model;

import java.sql.Timestamp;

public class ContenutoEntity {

    private Long idContenuto;
    private String cf;
    private Long idCartella;
    private String nome;
    private String tipo;
    private byte[] fileDati;
    private Long dimensione;
    private Timestamp dataCaricamento;
    private String privacy;

    public ContenutoEntity() {
    }

    public ContenutoEntity(String cf, String nome, String tipo, byte[] fileDati,
                           Long dimensione, String privacy) {
        this.cf = cf;
        this.nome = nome;
        this.tipo = tipo;
        this.fileDati = fileDati;
        this.dimensione = dimensione;
        this.privacy = privacy;
    }

    public Long getIdContenuto() { return idContenuto; }
    public void setIdContenuto(Long idContenuto) { this.idContenuto = idContenuto; }

    public String getCf() { return cf; }
    public void setCf(String cf) { this.cf = cf; }

    public Long getIdCartella() { return idCartella; }
    public void setIdCartella(Long idCartella) { this.idCartella = idCartella; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public byte[] getFileDati() { return fileDati; }
    public void setFileDati(byte[] fileDati) { this.fileDati = fileDati; }

    public Long getDimensione() { return dimensione; }
    public void setDimensione(Long dimensione) { this.dimensione = dimensione; }

    public Timestamp getDataCaricamento() { return dataCaricamento; }
    public void setDataCaricamento(Timestamp dataCaricamento) { this.dataCaricamento = dataCaricamento; }

    public String getPrivacy() { return privacy; }
    public void setPrivacy(String privacy) { this.privacy = privacy; }

    @Override
    public String toString() {
        return "ContenutoEntity{id=" + idContenuto + ", nome='" + nome + "', tipo='" + tipo + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContenutoEntity)) return false;
        return idContenuto != null && idContenuto.equals(((ContenutoEntity) o).idContenuto);
    }

    @Override
    public int hashCode() {
        return idContenuto != null ? idContenuto.hashCode() : 0;
    }
}
