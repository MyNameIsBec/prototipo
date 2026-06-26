package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.SessionManager;
import com.portfolioafam.util.ValidationUtils;

import java.sql.SQLException;

public class ModificaEmailCTRL {

    private final StudenteRepository studenteRepository;

    public ModificaEmailCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public void modificaEmail(String nuovaEmail) throws SQLException, ValidationException {
        if (!ValidationUtils.isValidEmail(nuovaEmail)) {
            throw new ValidationException("Formato non valido");
        }
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));

        if (nuovaEmail.equals(s.getEmail())) {
            throw new ValidationException("La nuova email deve essere diversa da quella attuale");
        }

        s.setEmail(nuovaEmail);
        studenteRepository.save(s);
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
