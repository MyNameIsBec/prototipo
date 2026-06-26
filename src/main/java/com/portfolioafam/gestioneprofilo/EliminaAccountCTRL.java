package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.PasswordUtils;
import com.portfolioafam.util.SessionManager;

import java.sql.Date;
import java.sql.SQLException;

public class EliminaAccountCTRL {

    private static final int GIORNI_RECUPERO = 14;

    private final StudenteRepository studenteRepository;

    public EliminaAccountCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public void verificaPassword(String password) throws SQLException, ValidationException {
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        if (!PasswordUtils.verifyPassword(s.getHashPassword(), password)) {
            throw new ValidationException("Password non corretta");
        }
    }

    public void eliminaAccount() throws SQLException {
        String cf = SessionManager.getInstance().getCf();
        studenteRepository.deleteByCf(cf);
        SessionManager.getInstance().terminaSessione();
    }

    public void ripristinaAccount(String cf) throws SQLException {
        studenteRepository.findByCf(cf).ifPresent(s -> {
            try {
                studenteRepository.save(s);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
