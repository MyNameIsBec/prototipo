package com.portfolioafam.util;

public class VisibilityUtils {

    public static final String PUBBLICO = "PUBBLICO";
    public static final String PRIVATO = "PRIVATO";
    public static final String PUBBLICO_SCARICABILE = "PUBBLICO_SCARICABILE";

    private VisibilityUtils() {
    }

    public static boolean isVisibile(String privacy) {
        return PUBBLICO.equals(privacy) || PUBBLICO_SCARICABILE.equals(privacy);
    }

    public static boolean isScaricabile(String privacy) {
        return PUBBLICO_SCARICABILE.equals(privacy);
    }

    public static boolean isValidPrivacy(String privacy) {
        return PUBBLICO.equals(privacy)
            || PRIVATO.equals(privacy)
            || PUBBLICO_SCARICABILE.equals(privacy);
    }
}
