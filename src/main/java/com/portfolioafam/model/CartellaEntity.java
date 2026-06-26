package com.portfolioafam.model;

public class CartellaEntity {

    private Long idCartella;
    private String cf;
    private String nomeCartella;
    private String privacy;

    public CartellaEntity() {
    }

    public CartellaEntity(String cf, String nomeCartella, String privacy) {
        this.cf = cf;
        this.nomeCartella = nomeCartella;
        this.privacy = privacy;
    }

    public Long getIdCartella() { return idCartella; }
    public void setIdCartella(Long idCartella) { this.idCartella = idCartella; }

    public String getCf() { return cf; }
    public void setCf(String cf) { this.cf = cf; }

    public String getNomeCartella() { return nomeCartella; }
    public void setNomeCartella(String nomeCartella) { this.nomeCartella = nomeCartella; }

    public String getPrivacy() { return privacy; }
    public void setPrivacy(String privacy) { this.privacy = privacy; }

    @Override
    public String toString() {
        return "CartellaEntity{id=" + idCartella + ", nome='" + nomeCartella + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartellaEntity)) return false;
        return idCartella != null && idCartella.equals(((CartellaEntity) o).idCartella);
    }

    @Override
    public int hashCode() {
        return idCartella != null ? idCartella.hashCode() : 0;
    }
}
