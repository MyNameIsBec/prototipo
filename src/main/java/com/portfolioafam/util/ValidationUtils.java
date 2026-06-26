package com.portfolioafam.util;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern CF_PATTERN =
            Pattern.compile("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$");

    private static final int MIN_PASSWORD_LENGTH = 12;
    private static final Pattern PASSWORD_UPPER = Pattern.compile("[A-Z]");
    private static final Pattern PASSWORD_LOWER = Pattern.compile("[a-z]");
    private static final Pattern PASSWORD_DIGIT = Pattern.compile("[0-9]");
    private static final Pattern PASSWORD_SPECIAL = Pattern.compile("[^A-Za-z0-9]");

    private ValidationUtils() {
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidCF(String cf) {
        return cf != null && CF_PATTERN.matcher(cf.toUpperCase()).matches();
    }

    public static String validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return "La password deve contenere almeno " + MIN_PASSWORD_LENGTH + " caratteri";
        }
        if (!PASSWORD_UPPER.matcher(password).find()) {
            return "La password deve contenere almeno una lettera maiuscola";
        }
        if (!PASSWORD_LOWER.matcher(password).find()) {
            return "La password deve contenere almeno una lettera minuscola";
        }
        if (!PASSWORD_DIGIT.matcher(password).find()) {
            return "La password deve contenere almeno un numero";
        }
        if (!PASSWORD_SPECIAL.matcher(password).find()) {
            return "La password deve contenere almeno un carattere speciale";
        }
        return null;
    }

    public static boolean isPasswordValid(String password) {
        return validatePassword(password) == null;
    }

    public static boolean isValidTelefono(String telefono) {
        return telefono != null && telefono.matches("^[+]?[0-9]{7,15}$");
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
