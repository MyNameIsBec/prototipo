package com.portfolioafam.autenticazione;

import com.portfolioafam.service.OTPService;

public class Verifica2faCTRL {

    private final OTPService otpService;

    public Verifica2faCTRL(OTPService otpService) {
        this.otpService = otpService;
    }

    public String generaEOttieniOTP(String chiave) {
        return otpService.generaEOttieniOTP(chiave);
    }

    public boolean verificaOTP(String chiave, String otpInserito) {
        return otpService.verificaOTP(chiave, otpInserito);
    }

    public void invalidaOTP(String chiave) {
        otpService.invalidaOTP(chiave);
    }
}
