package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ModificaTelefonoBND {

    @FXML private TextField telefonoField;

    private ModificaTelefonoCTRL modificaTelefonoCtrl;

    public ModificaTelefonoBND() {
    }

    public void setModificaTelefonoCtrl(ModificaTelefonoCTRL ctrl) {
        this.modificaTelefonoCtrl = ctrl;
    }

    @FXML
    private void handleSalva() {
        String nuovoTelefono = telefonoField.getText();
        try {
            if (modificaTelefonoCtrl != null) {
                modificaTelefonoCtrl.modificaTelefono(nuovoTelefono);
                AlertUtils.mostraMessaggio("Modifica telefono", "Numero aggiornato con successo");
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
