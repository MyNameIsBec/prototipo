package com.portfolioafam.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

    private AlertUtils() {
    }

    public static void mostraMessaggio(String titolo, String messaggio) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Portfolio AFAM");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public static void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Portfolio AFAM");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public static boolean mostraConferma(String titolo, String messaggio) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Portfolio AFAM");
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        return alert.showAndWait().map(r -> r.getButtonData().isDefaultButton()).orElse(false);
    }
}
