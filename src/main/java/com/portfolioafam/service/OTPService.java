package com.portfolioafam.service;

import com.portfolioafam.util.OTPUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OTPService {

    private static final long DURATA_OTP_SECONDS = 60;

    private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();

    public String generaEOttieniOTP(String chiave) {
        String otp = OTPUtil.generaOTP();
        long timestamp = OTPUtil.getUnixTime();
        otpStore.put(chiave, new OtpEntry(otp, timestamp));
        return otp;
    }

    public boolean verificaOTP(String chiave, String otpInserito) {
        OtpEntry entry = otpStore.get(chiave);
        if (entry == null) {
            return false;
        }
        if (!OTPUtil.isOTPValida(entry.timestamp, DURATA_OTP_SECONDS)) {
            otpStore.remove(chiave);
            return false;
        }
        if (!entry.otp.equals(otpInserito)) {
            return false;
        }
        otpStore.remove(chiave);
        return true;
    }

    public void invalidaOTP(String chiave) {
        otpStore.remove(chiave);
    }

    private static class OtpEntry {
        final String otp;
        final long timestamp;

        OtpEntry(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }
}
