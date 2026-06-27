package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.service.EmailService;
import com.portfolioafam.util.PasswordUtils;
import com.portfolioafam.util.SessionManager;

import java.sql.SQLException;

public class EliminaAccountCTRL {

    static final int GIORNI_RECUPERO = 14;

    private final StudenteRepository studenteRepository;
    private final EmailService emailService;

    public EliminaAccountCTRL(StudenteRepository studenteRepository, EmailService emailService) {
        this.studenteRepository = studenteRepository;
        this.emailService = emailService;
    }

    public void verificaPassword(String password) throws SQLException, ValidationException {
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        if (!PasswordUtils.verifyPassword(s.getHashPassword(), password)) {
            throw new ValidationException("Password non corretta");
        }
    }

    public void softDeleteAccount() throws SQLException {
        String cf = SessionManager.getInstance().getCf();
        studenteRepository.softDeleteByCf(cf);
    }

    public void inviaLinkRecupero() {
        String cf = SessionManager.getInstance().getCf();
        try {
            StudenteEntity s = studenteRepository.findByCfIncludingDeleted(cf).orElse(null);
            if (s != null && s.getEmail() != null) {
                String link = "http://localhost:8080/recupera?cf=" + cf;
                emailService.inviaLinkRecuperoAccount(s.getEmail(), link);
            }
        } catch (Exception e) {
            System.err.println("[EMAIL] Impossibile inviare link recupero: " + e.getMessage());
        }
    }

    public boolean checkAccountEliminato(String cf) throws SQLException {
        return studenteRepository.findDeletedByCf(cf).isPresent();
    }

    public void ripristinaAccount(String cf) throws SQLException {
        studenteRepository.ripristinaByCf(cf);
        SessionManager.getInstance().avviaSessioneStudente(
            studenteRepository.findByCf(cf).orElseThrow(() -> new IllegalStateException("Profilo non trovato dopo ripristino"))
        );
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
