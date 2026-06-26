package com.portfolioafam.autenticazione;

import com.portfolioafam.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SchermataOTPBND {

    @FXML private TextField email2faField;
    @FXML private TextField otpField;
    @FXML private Button inviaButton;
    @FXML private Button verificaButton;
    @FXML private Label messaggioLabel;

    private Verifica2faCTRL verifica2faCtrl;
    private String chiave;

    public SchermataOTPBND() {
    }

    public void setVerifica2faCtrl(Verifica2faCTRL ctrl) {
        this.verifica2faCtrl = ctrl;
    }

    @FXML
    private void handleInviaOTP() {
        String email = email2faField.getText();
        if (email == null || email.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Inserisci l'email per la verifica");
            return;
        }
        this.chiave = email;
        if (verifica2faCtrl != null) {
            verifica2faCtrl.generaEOttieniOTP(chiave);
            messaggioLabel.setText("OTP inviata");
        }
    }

    @FXML
    private void handleVerificaOTP() {
        String otp = otpField.getText();
        if (otp == null || otp.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Inserisci il codice OTP");
            return;
        }
        if (verifica2faCtrl != null && chiave != null) {
            boolean ok = verifica2faCtrl.verificaOTP(chiave, otp);
            if (ok) {
                messaggioLabel.setText("Verifica riuscita");
            } else {
                messaggioLabel.setText("Codice errato");
            }
        }
    }
}
