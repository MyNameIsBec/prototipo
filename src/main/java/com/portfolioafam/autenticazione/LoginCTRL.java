package com.portfolioafam.autenticazione;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.model.AmministratoreEntity;
import com.portfolioafam.service.AuthService;
import com.portfolioafam.util.SessionManager;
import com.portfolioafam.util.ValidationUtils;

import java.sql.SQLException;

public class LoginCTRL {

    private final AuthService authService;

    public LoginCTRL(AuthService authService) {
        this.authService = authService;
    }

    public LoginResult eseguiLogin(String email, String password) throws SQLException {
        if (!ValidationUtils.isValidEmail(email)) {
            return LoginResult.errore("Formato della email non valido");
        }

        try {
            StudenteEntity studente = authService.loginStudente(email, password);
            return LoginResult.autenticatoStudente(studente);
        } catch (AuthService.AuthException e1) {
            try {
                AmministratoreEntity admin = authService.loginAmministratore(email, password);
                return LoginResult.autenticatoAmministratore(admin);
            } catch (AuthService.AuthException e2) {
                return LoginResult.errore("Le credenziali non corrispondono, riprova");
            }
        }
    }

    public void completaLoginStudente(StudenteEntity s) {
        SessionManager.getInstance().avviaSessioneStudente(s);
    }

    public void completaLoginAmministratore(AmministratoreEntity a) {
        SessionManager.getInstance().avviaSessioneAmministratore(a);
    }

    public static class LoginResult {
        private final boolean successo;
        private final String messaggioErrore;
        private final StudenteEntity studente;
        private final AmministratoreEntity amministratore;
        private final boolean isStudente;

        private LoginResult(boolean successo, String messaggioErrore,
                            StudenteEntity studente, AmministratoreEntity amministratore,
                            boolean isStudente) {
            this.successo = successo;
            this.messaggioErrore = messaggioErrore;
            this.studente = studente;
            this.amministratore = amministratore;
            this.isStudente = isStudente;
        }

        public static LoginResult autenticatoStudente(StudenteEntity s) {
            return new LoginResult(true, null, s, null, true);
        }

        public static LoginResult autenticatoAmministratore(AmministratoreEntity a) {
            return new LoginResult(true, null, null, a, false);
        }

        public static LoginResult errore(String messaggio) {
            return new LoginResult(false, messaggio, null, null, false);
        }

        public boolean isSuccesso() { return successo; }
        public String getMessaggioErrore() { return messaggioErrore; }
        public StudenteEntity getStudente() { return studente; }
        public AmministratoreEntity getAmministratore() { return amministratore; }
        public boolean isStudente() { return isStudente; }
    }
}
