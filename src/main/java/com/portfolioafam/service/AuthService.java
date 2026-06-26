package com.portfolioafam.service;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.model.AmministratoreEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.repository.AmministratoreRepository;
import com.portfolioafam.util.PasswordUtils;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {

    private final StudenteRepository studenteRepository;
    private final AmministratoreRepository amministratoreRepository;

    public AuthService(StudenteRepository studenteRepository,
                       AmministratoreRepository amministratoreRepository) {
        this.studenteRepository = studenteRepository;
        this.amministratoreRepository = amministratoreRepository;
    }

    public StudenteEntity loginStudente(String email, String password) throws AuthException, SQLException {
        if (password == null) throw new AuthException("Le credenziali non corrispondono, riprova");
        Optional<StudenteEntity> opt = studenteRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new AuthException("Le credenziali non corrispondono, riprova");
        }
        StudenteEntity s = opt.get();
        if (!PasswordUtils.verifyPassword(s.getHashPassword(), password)) {
            throw new AuthException("Le credenziali non corrispondono, riprova");
        }
        return s;
    }

    public AmministratoreEntity loginAmministratore(String email, String password) throws AuthException, SQLException {
        if (password == null) throw new AuthException("Le credenziali non corrispondono, riprova");
        Optional<AmministratoreEntity> opt = amministratoreRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new AuthException("Le credenziali non corrispondono, riprova");
        }
        AmministratoreEntity a = opt.get();
        if (!PasswordUtils.verifyPassword(a.getHashPassword(), password)) {
            throw new AuthException("Le credenziali non corrispondono, riprova");
        }
        return a;
    }

    public boolean isCfRegistrato(String cf) throws SQLException {
        return studenteRepository.findByCf(cf).isPresent();
    }

    public void registraStudente(StudenteEntity s) throws AuthException, SQLException {
        if (isCfRegistrato(s.getCf())) {
            throw new AuthException("Questo utente risulta già registrato");
        }
        s.setHashPassword(PasswordUtils.hashPassword(s.getHashPassword()));
        studenteRepository.save(s);
    }

    public static class AuthException extends Exception {
        public AuthException(String message) {
            super(message);
        }
    }
}
