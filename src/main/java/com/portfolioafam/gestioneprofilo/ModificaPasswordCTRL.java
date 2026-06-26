package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.PasswordUtils;
import com.portfolioafam.util.SessionManager;
import com.portfolioafam.util.ValidationUtils;

import java.sql.SQLException;

public class ModificaPasswordCTRL {

    private final StudenteRepository studenteRepository;

    public ModificaPasswordCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public void verificaPasswordAttuale(String password) throws SQLException, ValidationException {
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        if (!PasswordUtils.verifyPassword(s.getHashPassword(), password)) {
            throw new ValidationException("Le credenziali non corrispondono, riprova");
        }
    }

    public void modificaPassword(String nuovaPassword) throws SQLException, ValidationException {
        String errore = ValidationUtils.validatePassword(nuovaPassword);
        if (errore != null) {
            throw new ValidationException(errore);
        }
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        s.setHashPassword(PasswordUtils.hashPassword(nuovaPassword));
        studenteRepository.save(s);
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
