package com.portfolioafam.service;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.PasswordUtils;

import java.sql.SQLException;
import java.util.Optional;

public class PasswordResetService {

    private final StudenteRepository studenteRepository;

    public PasswordResetService(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public StudenteEntity verificaUtente(String email) throws PasswordResetException, SQLException {
        Optional<StudenteEntity> opt = studenteRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new PasswordResetException("Nessun account associato a questa email");
        }
        return opt.get();
    }

    public void resetPassword(String cf, String nuovaPassword) throws PasswordResetException, SQLException {
        Optional<StudenteEntity> opt = studenteRepository.findByCf(cf);
        if (opt.isEmpty()) {
            throw new PasswordResetException("Account non trovato");
        }
        StudenteEntity s = opt.get();
        s.setHashPassword(PasswordUtils.hashPassword(nuovaPassword));
        studenteRepository.save(s);
    }

    public static class PasswordResetException extends Exception {
        public PasswordResetException(String message) {
            super(message);
        }
    }
}
