package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ModificaEmailBND {

    @FXML private TextField emailField;
    @FXML private Button salvaButton;

    private ModificaEmailCTRL modificaEmailCtrl;

    public ModificaEmailBND() {
    }

    public void setModificaEmailCtrl(ModificaEmailCTRL ctrl) {
        this.modificaEmailCtrl = ctrl;
    }

    @FXML
    private void handleSalva() {
        String nuovaEmail = emailField.getText();
        try {
            if (modificaEmailCtrl != null) {
                modificaEmailCtrl.modificaEmail(nuovaEmail);
                AlertUtils.mostraMessaggio("Modifica email", "Email aggiornata con successo");
                SceneManager.switchTo("SchermataProfilo");
            }
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", e.getMessage());
        }
    }

    @FXML
    private void handleIndietro() {
        SceneManager.switchTo("SchermataProfilo");
    }
}
