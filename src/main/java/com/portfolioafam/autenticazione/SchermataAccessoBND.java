package com.portfolioafam.autenticazione;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.Config;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataAccessoBND {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    private LoginCTRL loginCtrl;
    private SchermataVerifica2FABND verifica2faBnd;
    private boolean loginAsAdmin;

    public SchermataAccessoBND() {}
    public void setLoginCtrl(LoginCTRL c) { this.loginCtrl = c; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }

    @FXML private void handleAccedi() {
        String email = emailField.getText();
        String password = passwordField.getText();
        System.err.println("[LOGIN DEBUG] handleAccedi: email=" + email);
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Inserisci email e password"); return;
        }
        if (loginCtrl == null) { System.err.println("[LOGIN DEBUG] loginCtrl è null!"); AlertUtils.mostraErrore("Errore", "Servizio login non disponibile"); return; }
        try {
            LoginCTRL.LoginResult r = loginCtrl.eseguiLogin(email, password);
            System.err.println("[LOGIN DEBUG] LoginResult: successo=" + r.isSuccesso() + " isStudente=" + r.isStudente());
            if (!r.isSuccesso()) { AlertUtils.mostraErrore("Errore", r.getMessaggioErrore()); return; }

            if (r.isStudente()) loginCtrl.completaLoginStudente(r.getStudente());
            else { loginCtrl.completaLoginAmministratore(r.getAmministratore()); loginAsAdmin = true; }
            System.err.println("[LOGIN DEBUG] Sessione avviata, 2faEnabled=" + Config.is2faEnabled());

            Runnable dopo2fa = () -> {
                if (loginAsAdmin) {
                    AlertUtils.mostraMessaggio("Accesso", "Verifica riuscita");
                    SceneManager.switchTo("AdminDashboard");
                } else {
                    SceneManager.switchTo("SchermataProfilo");
                }
            };

            if (!Config.is2faEnabled()) {
                System.err.println("[LOGIN DEBUG] 2FA disabilitata, eseguo dopo2fa direttamente");
                dopo2fa.run();
                return;
            }
            if (verifica2faBnd == null) { dopo2fa.run(); return; }
            verifica2faBnd.setOnVerificaSuccesso(dopo2fa);
            verifica2faBnd.setChiave(email);
            SceneManager.switchTo("SchermataVerifica2FA");
        } catch (Exception e) { 
            System.err.println("[LOGIN DEBUG] Eccezione: " + e.getClass().getName() + ": " + e.getMessage());
            AlertUtils.mostraErrore("Errore", "Connessione al server interrotta"); 
        }
    }
    @FXML private void handleVaiAHome() { SceneManager.switchTo("HomePage"); }
    @FXML private void handleVaiARegistrazione() { SceneManager.switchTo("FormRegistrazione"); }
    @FXML private void handleRecuperaPassword() { SceneManager.switchTo("FormRecuperoPassword"); }
    @FXML private void handleAccediSPID() { SceneManager.switchTo("SchermataSPID"); }
    @FXML private void handleAccediEIDAS() { SceneManager.switchTo("SchermataEIDAS"); }
}
