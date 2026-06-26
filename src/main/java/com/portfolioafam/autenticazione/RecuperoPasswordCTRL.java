package com.portfolioafam.autenticazione;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.service.PasswordResetService;

import java.sql.SQLException;

public class RecuperoPasswordCTRL {

    private final PasswordResetService passwordResetService;

    public RecuperoPasswordCTRL(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    public StudenteEntity verificaUtentePerRecupero(String email)
            throws PasswordResetService.PasswordResetException, SQLException {
        return passwordResetService.verificaUtente(email);
    }

    public void resetPassword(String cf, String nuovaPassword)
            throws PasswordResetService.PasswordResetException, SQLException {
        passwordResetService.resetPassword(cf, nuovaPassword);
    }
}
