package com.portfolioafam.autenticazione;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataVerifica2FABND {
    @FXML private TextField otpField;
    @FXML private Button verificaButton;
    @FXML private Label messaggioLabel;
    private Verifica2faCTRL verifica2faCtrl;
    private String chiave;
    private Runnable onVerificaSuccesso;

    public SchermataVerifica2FABND() {}
    public void setVerifica2faCtrl(Verifica2faCTRL c) { this.verifica2faCtrl = c; }
    public void setOnVerificaSuccesso(Runnable r) { this.onVerificaSuccesso = r; }
    public Runnable getOnVerificaSuccesso() { return onVerificaSuccesso; }
    public void setChiave(String chiave) {
        this.chiave = chiave;
        if (verifica2faCtrl != null) verifica2faCtrl.generaEOttieniOTP(chiave);
    }
    @FXML private void handleVerifica() {
        String otp = otpField.getText();
        if (otp == null || otp.isEmpty()) { AlertUtils.mostraErrore("Errore", "Inserisci il codice di verifica"); return; }
        if (verifica2faCtrl != null && chiave != null) {
            boolean ok = verifica2faCtrl.verificaOTP(chiave, otp);
            if (ok) {
                messaggioLabel.setText("Verifica riuscita");
                if (onVerificaSuccesso != null) onVerificaSuccesso.run();
            } else {
                messaggioLabel.setText("Codice errato");
                verifica2faCtrl.generaEOttieniOTP(chiave);
            }
        }
    }
}
