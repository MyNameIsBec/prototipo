package com.portfolioafam.util;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PasswordFieldUtils {

    public static void addToggle(PasswordField pwField) {
        String prompt = pwField.getPromptText();
        String style = pwField.getStyle();
        double prefW = pwField.getPrefWidth();
        double maxW = pwField.getMaxWidth();
        double prefH = pwField.getPrefHeight();

        TextField txtField = new TextField();
        txtField.setManaged(false);
        txtField.setVisible(false);
        txtField.setPromptText(prompt);
        txtField.setStyle(style);
        txtField.setPrefWidth(prefW);
        txtField.setPrefHeight(prefH);
        txtField.setMaxWidth(maxW);
        txtField.textProperty().bindBidirectional(pwField.textProperty());
        HBox.setHgrow(txtField, javafx.scene.layout.Priority.ALWAYS);

        ToggleButton toggleBtn = new ToggleButton("👁");
        toggleBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 16px; -fx-padding: 4 6; -fx-border-color: #ddd; -fx-border-radius: 4;");
        toggleBtn.setFocusTraversable(false);

        HBox.setHgrow(pwField, javafx.scene.layout.Priority.ALWAYS);
        pwField.setMaxWidth(Double.MAX_VALUE);

        toggleBtn.selectedProperty().addListener((obs, old, show) -> {
            pwField.setManaged(!show);
            pwField.setVisible(!show);
            txtField.setManaged(show);
            txtField.setVisible(show);
            if (show) txtField.requestFocus();
        });

        Node parent = pwField.getParent();
        if (parent instanceof Pane) {
            Pane pane = (Pane) parent;
            int idx = pane.getChildren().indexOf(pwField);
            if (idx >= 0) {
                HBox box = new HBox(4);
                box.setMaxWidth(maxW);
                HBox.setHgrow(box, javafx.scene.layout.Priority.ALWAYS);
                pane.getChildren().remove(pwField);
                box.getChildren().addAll(pwField, txtField, toggleBtn);
                if (idx <= pane.getChildren().size())
                    pane.getChildren().add(idx, box);
                else
                    pane.getChildren().add(box);
            }
        }
    }
}
