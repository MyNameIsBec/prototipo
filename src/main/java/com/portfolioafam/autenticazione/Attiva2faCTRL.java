package com.portfolioafam.autenticazione;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.service.OTPService;

import java.sql.SQLException;

public class Attiva2faCTRL {

    private final StudenteRepository studenteRepository;
    private final OTPService otpService;

    public Attiva2faCTRL(StudenteRepository studenteRepository, OTPService otpService) {
        this.studenteRepository = studenteRepository;
        this.otpService = otpService;
    }

    public String generaEOttieniOTP(String chiave) {
        return otpService.generaEOttieniOTP(chiave);
    }

    public boolean verificaOTP(String chiave, String otp) {
        return otpService.verificaOTP(chiave, otp);
    }

    public void salvaEmail2FA(String cf, String email2fa) throws SQLException {
        studenteRepository.findByCf(cf).ifPresent(s -> {
            s.setEmail2fa(email2fa);
            try {
                studenteRepository.save(s);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
