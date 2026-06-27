package com.portfolioafam.gestioneprofilo;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.PasswordFieldUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class ConfermaEliminazioneBND {
    @FXML private PasswordField passwordField;
    private EliminaAccountCTRL eliminaAccountCtrl;
    public ConfermaEliminazioneBND() {}
    @FXML private void initialize() {
        PasswordFieldUtils.addToggle(passwordField);
    }
    public void setEliminaAccountCtrl(EliminaAccountCTRL c) { this.eliminaAccountCtrl = c; }
    @FXML private void handleConfermaEliminazione() {
        try {
            eliminaAccountCtrl.verificaPassword(passwordField.getText());
            eliminaAccountCtrl.eliminaAccount();
            AlertUtils.mostraMessaggio("Account eliminato", "Account eliminato. Hai 14 giorni per recuperarlo");
            SceneManager.switchTo("HomePage");
        } catch (EliminaAccountCTRL.ValidationException e) {
            AlertUtils.mostraErrore("Errore", e.getMessage());
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", "Operazione non riuscita");
        }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataProfilo"); }
}
