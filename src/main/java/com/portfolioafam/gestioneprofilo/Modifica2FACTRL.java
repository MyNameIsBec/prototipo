package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.service.OTPService;
import com.portfolioafam.util.SessionManager;

import java.sql.SQLException;

public class Modifica2FACTRL {

    private final StudenteRepository studenteRepository;
    private final OTPService otpService;

    public Modifica2FACTRL(StudenteRepository studenteRepository, OTPService otpService) {
        this.studenteRepository = studenteRepository;
        this.otpService = otpService;
    }

    public String generaEOttieniOTP(String chiave) {
        return otpService.generaEOttieniOTP(chiave);
    }

    public boolean verificaOTP(String chiave, String otp) {
        return otpService.verificaOTP(chiave, otp);
    }

    public void salvaNuovaEmail2FA(String nuovaEmail2fa) throws SQLException {
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        s.setEmail2fa(nuovaEmail2fa);
        studenteRepository.save(s);
    }
}
