package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecuperaAccountBND {

    @FXML private TextField cfField;
    @FXML private TextField emailField;

    private RecuperaAccountCTRL recuperaAccountCtrl;

    public RecuperaAccountBND() {
    }

    public void setRecuperaAccountCtrl(RecuperaAccountCTRL ctrl) {
        this.recuperaAccountCtrl = ctrl;
    }

    @FXML
    private void handleRecupera() {
        String cf = cfField.getText().toUpperCase();
        String email = emailField.getText();

        if (cf.isEmpty() || email.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Compila tutti i campi");
            return;
        }

        try {
            if (recuperaAccountCtrl != null) {
                recuperaAccountCtrl.verificaDati(cf, email);
                if (AlertUtils.mostraConferma("Recupero account",
                        "Vuoi recuperare il tuo account?")) {
                    recuperaAccountCtrl.recuperaAccount(cf);
                    AlertUtils.mostraMessaggio("Recupero account", "Recupero account riuscito");
                    SceneManager.switchTo("HomePage");
                }
            }
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", e.getMessage());
        }
    }

    @FXML
    private void handleIndietro() {
        SceneManager.switchTo("HomePage");
    }
}
