package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;

import java.sql.SQLException;
import java.util.Optional;

public class RecuperaAccountCTRL {

    private final StudenteRepository studenteRepository;

    public RecuperaAccountCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public StudenteEntity verificaDati(String cf, String email) throws SQLException, ValidationException {
        Optional<StudenteEntity> opt = studenteRepository.findByCf(cf);
        if (opt.isEmpty()) {
            throw new ValidationException("Account non trovato");
        }
        StudenteEntity s = opt.get();
        if (!s.getEmail().equals(email)) {
            throw new ValidationException("I dati non corrispondono");
        }
        return s;
    }

    public void recuperaAccount(String cf) throws SQLException {
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Account non trovato"));
        studenteRepository.save(s);
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
