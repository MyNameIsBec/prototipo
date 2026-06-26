package com.portfolioafam.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtils {

    private static final Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id, 32, 64);

    private static final int ITERATIONS = 10;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    private PasswordUtils() {
    }

    public static String hashPassword(String plainPassword) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, plainPassword.toCharArray());
    }

    public static boolean verifyPassword(String hash, String plainPassword) {
        return argon2.verify(hash, plainPassword.toCharArray());
    }
}
