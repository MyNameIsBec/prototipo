package com.portfolioafam.util;

import java.security.SecureRandom;

public class OTPUtil {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    private OTPUtil() {
    }

    public static String generaOTP() {
        int otp = secureRandom.nextInt(1_000_000);
        return String.format("%06d", otp);
    }

    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static boolean isOTPValida(long timestampGenerazione, long durataSecondi) {
        long ora = getUnixTime();
        return (ora - timestampGenerazione) <= durataSecondi;
    }
}
