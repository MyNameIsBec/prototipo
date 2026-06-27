package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RecuperaAccountBND {

    @FXML private TextField cfField;
    @FXML private TextField emailField;

    private EliminaAccountCTRL eliminaAccountCtrl;

    public RecuperaAccountBND() {
    }

    public void setEliminaAccountCtrl(EliminaAccountCTRL ctrl) {
        this.eliminaAccountCtrl = ctrl;
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
            if (eliminaAccountCtrl != null) {
                if (!eliminaAccountCtrl.checkAccountEliminato(cf)) {
                    AlertUtils.mostraErrore("Errore", "Account non trovato o non eliminato");
                    return;
                }
                if (AlertUtils.mostraConferma("Recupero account",
                        "Procedendo accetti il trattamento dei dati personali secondo l'informativa GDPR.\nVuoi recuperare il tuo account?")) {
                    eliminaAccountCtrl.ripristinaAccount(cf);
                    AlertUtils.mostraMessaggio("Recupero account", "Recupero account riuscito");
                    SceneManager.switchTo("SchermataProfilo");
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
