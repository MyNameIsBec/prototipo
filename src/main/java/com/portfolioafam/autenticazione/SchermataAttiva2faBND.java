package com.portfolioafam.autenticazione;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataAttiva2faBND {
    @FXML private TextField email2faField, otpField;
    @FXML private Button inviaButton, verificaButton;
    @FXML private Label messaggioLabel;
    private Attiva2faCTRL attiva2faCtrl;
    public SchermataAttiva2faBND() {}
    public void setAttiva2faCtrl(Attiva2faCTRL c) { this.attiva2faCtrl = c; }
    @FXML private void handleIndietro() { SceneManager.switchTo("FormPassword"); }
    @FXML private void handleInviaOTP() {
        String email = email2faField.getText();
        if (email == null || email.isEmpty()) { AlertUtils.mostraErrore("Errore", "Inserisci l'email"); return; }
        if (attiva2faCtrl != null) {
            String otp = attiva2faCtrl.generaEOttieniOTP(email);
            try {
                attiva2faCtrl.inviaOTPEmail(email, otp);
                messaggioLabel.setText("OTP inviata");
            } catch (Exception e) {
                AlertUtils.mostraErrore("Errore", "Impossibile inviare l'email di verifica");
            }
        }
    }
    @FXML private void handleVerificaOTP() {
        String otp = otpField.getText();
        String email = email2faField.getText();
        if (otp == null || otp.isEmpty()) { AlertUtils.mostraErrore("Errore", "Inserisci il codice OTP"); return; }
        if (attiva2faCtrl != null && attiva2faCtrl.verificaOTP(email, otp)) {
            try {
                attiva2faCtrl.salvaEmail2FA(SessionManager.getInstance().getCf(), email);
                AlertUtils.mostraMessaggio("2FA", "Attivazione riuscita");
                SceneManager.switchTo("SchermataProfilo");
            } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
        } else {
            messaggioLabel.setText("Codice errato");
            if (attiva2faCtrl != null) {
                String newOtp = attiva2faCtrl.generaEOttieniOTP(email);
                try {
                    attiva2faCtrl.inviaOTPEmail(email, newOtp);
                } catch (Exception e) { }
            }
        }
    }
}
