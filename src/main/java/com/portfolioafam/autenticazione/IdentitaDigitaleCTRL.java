package com.portfolioafam.autenticazione;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.service.AuthService;
import com.portfolioafam.util.SessionManager;

import java.sql.SQLException;

public class IdentitaDigitaleCTRL {

    private final AuthService authService;

    public IdentitaDigitaleCTRL(AuthService authService) {
        this.authService = authService;
    }

    public boolean isSPIDDisponibile() {
        return true;
    }

    public boolean isEIDASDisponibile() {
        return true;
    }

    public StudenteEntity autenticaConProvider(String email, String cf, String nome,
                                                String cognome, String datiAccademici,
                                                boolean giaRegistrato) throws AuthService.AuthException, SQLException {
        if (giaRegistrato) {
            StudenteEntity s = new StudenteEntity();
            s.setEmail(email);
            s.setCf(cf);
            s.setNome(nome);
            s.setCognome(cognome);
            SessionManager.getInstance().avviaSessioneStudente(s);
            return s;
        }

        StudenteEntity s = new StudenteEntity(cf, nome, cognome, email, "", "", "PRIVATO");
        s.setDatiAccademici(datiAccademici);
        authService.registraStudente(s);
        return s;
    }
}
