package com.portfolioafam.model;

import java.util.Arrays;

public class StudenteEntity {

    private String cf;
    private String nome;
    private String cognome;
    private String email;
    private String hashPassword;
    private String telefono;
    private String email2fa;
    private byte[] immagineProfilo;
    private String datiAccademici;
    private String visibilitaProfilo;

    public StudenteEntity() {
    }

    public StudenteEntity(String cf, String nome, String cognome, String email,
                          String hashPassword, String email2fa, String visibilitaProfilo) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.hashPassword = hashPassword;
        this.email2fa = email2fa;
        this.visibilitaProfilo = visibilitaProfilo;
    }

    public String getCf() { return cf; }
    public void setCf(String cf) { this.cf = cf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHashPassword() { return hashPassword; }
    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail2fa() { return email2fa; }
    public void setEmail2fa(String email2fa) { this.email2fa = email2fa; }

    public byte[] getImmagineProfilo() { return immagineProfilo; }
    public void setImmagineProfilo(byte[] immagineProfilo) { this.immagineProfilo = immagineProfilo; }

    public String getDatiAccademici() { return datiAccademici; }
    public void setDatiAccademici(String datiAccademici) { this.datiAccademici = datiAccademici; }

    public String getVisibilitaProfilo() { return visibilitaProfilo; }
    public void setVisibilitaProfilo(String visibilitaProfilo) { this.visibilitaProfilo = visibilitaProfilo; }

    @Override
    public String toString() {
        return "StudenteEntity{cf='" + cf + "', nome='" + nome + "', cognome='" + cognome + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudenteEntity)) return false;
        return cf != null && cf.equals(((StudenteEntity) o).cf);
    }

    @Override
    public int hashCode() {
        return cf != null ? cf.hashCode() : 0;
    }
}
