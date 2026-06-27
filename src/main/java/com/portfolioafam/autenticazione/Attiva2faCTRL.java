package com.portfolioafam.autenticazione;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.service.EmailService;
import com.portfolioafam.service.OTPService;
import jakarta.mail.MessagingException;

import java.sql.SQLException;

public class Attiva2faCTRL {

    private final StudenteRepository studenteRepository;
    private final OTPService otpService;
    private final EmailService emailService;

    public Attiva2faCTRL(StudenteRepository studenteRepository, OTPService otpService, EmailService emailService) {
        this.studenteRepository = studenteRepository;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    public String generaEOttieniOTP(String chiave) {
        return otpService.generaEOttieniOTP(chiave);
    }

    public void inviaOTPEmail(String destinatario, String otp) throws MessagingException {
        emailService.inviaOTP(destinatario, otp);
    }

    public boolean verificaOTP(String chiave, String otp) {
        return otpService.verificaOTP(chiave, otp);
    }

    public void salvaEmail2FA(String cf, String email2fa) throws SQLException {
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new SQLException("Studente non trovato per salvataggio email 2FA"));
        s.setEmail2fa(email2fa);
        studenteRepository.save(s);
    }
}
