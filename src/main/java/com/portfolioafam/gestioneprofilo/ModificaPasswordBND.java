package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ModificaPasswordBND {
    @FXML private PasswordField passwordAttualeField;
    @FXML private PasswordField nuovaPasswordField;
    private ModificaPasswordCTRL modificaPasswordCtrl;

    public ModificaPasswordBND() {}
    public void setModificaPasswordCtrl(ModificaPasswordCTRL c) { this.modificaPasswordCtrl = c; }

    @FXML private void handleSalva() {
        String attuale = passwordAttualeField.getText();
        String nuova = nuovaPasswordField.getText();
        try {
            if (modificaPasswordCtrl != null) {
                modificaPasswordCtrl.verificaPasswordAttuale(attuale);
                modificaPasswordCtrl.modificaPassword(nuova);
                AlertUtils.mostraMessaggio("Modifica password", "Password modificata con successo");
                SceneManager.switchTo("SchermataProfilo");
            }
        } catch (ModificaPasswordCTRL.ValidationException e) {
            AlertUtils.mostraErrore("Errore", e.getMessage());
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", "Connessione al server interrotta");
        }
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataProfilo"); }
}
