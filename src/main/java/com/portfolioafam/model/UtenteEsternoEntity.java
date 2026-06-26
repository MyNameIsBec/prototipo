package com.portfolioafam.model;

public class UtenteEsternoEntity {

    private Long idUtenteEsterno;
    private String nome;
    private String cognome;
    private String email;
    private String ruolo;
    private Integer riscontro;

    public UtenteEsternoEntity() {
    }

    public UtenteEsternoEntity(String nome, String cognome, String email, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo = ruolo;
    }

    public Long getIdUtenteEsterno() { return idUtenteEsterno; }
    public void setIdUtenteEsterno(Long idUtenteEsterno) { this.idUtenteEsterno = idUtenteEsterno; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public Integer getRiscontro() { return riscontro; }
    public void setRiscontro(Integer riscontro) { this.riscontro = riscontro; }

    @Override
    public String toString() {
        return "UtenteEsternoEntity{id=" + idUtenteEsterno + ", nome='" + nome + " " + cognome + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UtenteEsternoEntity)) return false;
        return idUtenteEsterno != null && idUtenteEsterno.equals(((UtenteEsternoEntity) o).idUtenteEsterno);
    }

    @Override
    public int hashCode() {
        return idUtenteEsterno != null ? idUtenteEsterno.hashCode() : 0;
    }
}
