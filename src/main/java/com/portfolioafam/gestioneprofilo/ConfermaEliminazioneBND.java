package com.portfolioafam.gestioneprofilo;
import com.portfolioafam.autenticazione.SchermataVerifica2FABND;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.PasswordFieldUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class ConfermaEliminazioneBND {
    @FXML private PasswordField passwordField;
    private EliminaAccountCTRL eliminaAccountCtrl;
    private SchermataVerifica2FABND verifica2faBnd;
    public ConfermaEliminazioneBND() {}
    @FXML private void initialize() {
        PasswordFieldUtils.addToggle(passwordField);
    }
    public void setEliminaAccountCtrl(EliminaAccountCTRL c) { this.eliminaAccountCtrl = c; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }
    @FXML private void handleConfermaEliminazione() {
        try {
            eliminaAccountCtrl.verificaPassword(passwordField.getText());
            if (verifica2faBnd != null) {
                verifica2faBnd.setPaginaPrecedente("SchermataProfilo");
                verifica2faBnd.setOnVerificaSuccesso(() -> {
                    try {
                        eliminaAccountCtrl.softDeleteAccount();
                        eliminaAccountCtrl.inviaLinkRecupero();
                        SessionManager.getInstance().terminaSessione();
                        AlertUtils.mostraMessaggio("Account eliminato", "Account eliminato. Hai 14 giorni per recuperarlo");
                        SceneManager.switchTo("HomePage");
                    } catch (Exception e) {
                        AlertUtils.mostraErrore("Errore", "Operazione non riuscita: " + e.getMessage());
                    }
                });
                SceneManager.switchToFresh("SchermataVerifica2FA");
            }
        } catch (EliminaAccountCTRL.ValidationException e) {
            AlertUtils.mostraErrore("Errore", "Password errata, riprova");
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", "Operazione non riuscita");
        }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataProfilo"); }
}
