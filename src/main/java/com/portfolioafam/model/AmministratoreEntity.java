package com.portfolioafam.model;

public class AmministratoreEntity {

    private Long idAmministratore;
    private String email;
    private String hashPassword;
    private String email2fa;

    public AmministratoreEntity() {
    }

    public AmministratoreEntity(String email, String hashPassword, String email2fa) {
        this.email = email;
        this.hashPassword = hashPassword;
        this.email2fa = email2fa;
    }

    public Long getIdAmministratore() { return idAmministratore; }
    public void setIdAmministratore(Long idAmministratore) { this.idAmministratore = idAmministratore; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHashPassword() { return hashPassword; }
    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }

    public String getEmail2fa() { return email2fa; }
    public void setEmail2fa(String email2fa) { this.email2fa = email2fa; }

    @Override
    public String toString() {
        return "AmministratoreEntity{id=" + idAmministratore + ", email='" + email + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmministratoreEntity)) return false;
        return idAmministratore != null && idAmministratore.equals(((AmministratoreEntity) o).idAmministratore);
    }

    @Override
    public int hashCode() {
        return idAmministratore != null ? idAmministratore.hashCode() : 0;
    }
}
