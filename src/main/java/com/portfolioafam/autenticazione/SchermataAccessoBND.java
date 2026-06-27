package com.portfolioafam.autenticazione;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.Config;
import com.portfolioafam.util.PasswordFieldUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.ValidationUtils;
import com.portfolioafam.visualizzastudente.SchermataProfiloStudenteBND;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataAccessoBND {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    private LoginCTRL loginCtrl;
    private SchermataVerifica2FABND verifica2faBnd;
    private boolean loginAsAdmin;
    private StudenteRepository studenteRepository;
    private ModificaPasswordPrimoAccessoBND modificaPasswordPrimoAccessoBnd;

    public SchermataAccessoBND() {}
    @FXML private void initialize() {
        PasswordFieldUtils.addToggle(passwordField);
    }
    public void setLoginCtrl(LoginCTRL c) { this.loginCtrl = c; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }
    public void setStudenteRepository(StudenteRepository r) { this.studenteRepository = r; }
    public void setModificaPasswordPrimoAccessoBnd(ModificaPasswordPrimoAccessoBND b) { this.modificaPasswordPrimoAccessoBnd = b; }

    @FXML private void handleAccedi() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Inserisci email e password"); return;
        }
        if (!ValidationUtils.isPasswordValid(password)) {
            AlertUtils.mostraErrore("Errore", "Formato della password errato, riprova"); return;
        }
        if (loginCtrl == null) { AlertUtils.mostraErrore("Errore", "Servizio login non disponibile"); return; }
        try {
            LoginCTRL.LoginResult r = loginCtrl.eseguiLogin(email, password);
            if (!r.isSuccesso()) { AlertUtils.mostraErrore("Errore", r.getMessaggioErrore()); return; }

            if (r.isStudente()) {
                loginCtrl.completaLoginStudente(r.getStudente());
                loginAsAdmin = false;

                if (r.getStudente().isPasswordTemporanea()) {
                    if (modificaPasswordPrimoAccessoBnd != null) {
                        modificaPasswordPrimoAccessoBnd.setStudente(r.getStudente());
                        modificaPasswordPrimoAccessoBnd.setStudenteRepository(studenteRepository);
                        modificaPasswordPrimoAccessoBnd.setVerifica2faBnd(verifica2faBnd);
                    }
                    SceneManager.switchTo("ModificaPasswordPrimoAccesso");
                    return;
                }

                verifica2faBnd.setChiave(email);
                verifica2faBnd.setStudenteRepository(studenteRepository);
                verifica2faBnd.setStudente(r.getStudente());
            } else {
                loginCtrl.completaLoginAmministratore(r.getAmministratore());
                loginAsAdmin = true;
            }

            Runnable dopo2fa = () -> {
                if (loginAsAdmin) {
                    AlertUtils.mostraMessaggio("Accesso", "Verifica riuscita");
                    SceneManager.switchTo("AdminDashboard");
                } else {
                    SceneManager.switchTo("SchermataProfilo");
                }
            };

            if (loginAsAdmin) {
                dopo2fa.run();
                return;
            }
            if (verifica2faBnd == null) { dopo2fa.run(); return; }
            verifica2faBnd.setOnVerificaSuccesso(dopo2fa);
            verifica2faBnd.setPaginaPrecedente("SchermataAccesso");
            SceneManager.switchTo("SchermataVerifica2FA");
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", "Connessione al server interrotta");
        }
    }
    @FXML private void handleVaiAHome() { SceneManager.switchTo("HomePage"); }
    @FXML private void handleVaiARegistrazione() { SceneManager.switchTo("FormRegistrazione"); }
    @FXML private void handleRecuperaPassword() { SceneManager.switchTo("FormRecuperoPassword"); }
    @FXML private void handleAccediSPID() { SceneManager.switchTo("SchermataSPID"); }
    @FXML private void handleAccediEIDAS() { SceneManager.switchTo("SchermataEIDAS"); }
}
