package com.portfolioafam.autenticazione;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.service.AuthService;
import com.portfolioafam.service.OTPService;

import java.sql.SQLException;

public class RegistrazioneCTRL {

    private final AuthService authService;
    private final OTPService otpService;

    public RegistrazioneCTRL(AuthService authService, OTPService otpService) {
        this.authService = authService;
        this.otpService = otpService;
    }

    public boolean isCfRegistrato(String cf) throws SQLException {
        return authService.isCfRegistrato(cf);
    }

    public void registra(StudenteEntity s) throws AuthService.AuthException, SQLException {
        authService.registraStudente(s);
    }

    public String generaOtp2FA(String email2fa) {
        return otpService.generaEOttieniOTP(email2fa);
    }

    public boolean verificaOtp2FA(String email2fa, String otp) {
        return otpService.verificaOTP(email2fa, otp);
    }
}
