package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.SessionManager;
import com.portfolioafam.util.ValidationUtils;

import java.sql.SQLException;

public class ModificaTelefonoCTRL {

    private final StudenteRepository studenteRepository;

    public ModificaTelefonoCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public void modificaTelefono(String nuovoTelefono) throws SQLException, ValidationException {
        if (nuovoTelefono != null && !nuovoTelefono.isEmpty()
                && !ValidationUtils.isValidTelefono(nuovoTelefono)) {
            throw new ValidationException("Formato numero di telefono non valido");
        }
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        s.setTelefono(nuovoTelefono);
        studenteRepository.save(s);
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
