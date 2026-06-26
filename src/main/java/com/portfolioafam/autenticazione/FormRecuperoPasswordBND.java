package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class FormRecuperoPasswordBND {
    @FXML private TextField emailField, cfField;
    @FXML private PasswordField nuovaPasswordField, confermaPasswordField;
    @FXML private Label statoLabel;
    private RecuperoPasswordCTRL recuperoPasswordCtrl;
    private String cfVerificato;
    public FormRecuperoPasswordBND() {}
    public void setRecuperoPasswordCtrl(RecuperoPasswordCTRL c) { this.recuperoPasswordCtrl = c; }
    @FXML private void handleConferma() {
        String email = emailField.getText();
        String cf = cfField.getText().toUpperCase();
        if (!ValidationUtils.isValidEmail(email)) { AlertUtils.mostraErrore("Errore", "Formato email non valido"); return; }
        if (!ValidationUtils.isNotEmpty(cf)) { AlertUtils.mostraErrore("Errore", "Inserisci il codice fiscale"); return; }
        try {
            if (recuperoPasswordCtrl != null) {
                StudenteEntity s = recuperoPasswordCtrl.verificaUtentePerRecupero(email);
                if (!s.getCf().equalsIgnoreCase(cf)) { AlertUtils.mostraErrore("Errore", "I dati non corrispondono"); return; }
                cfVerificato = cf;
                statoLabel.setText("Verifica riuscita. Inserisci la nuova password.");
                nuovaPasswordField.setVisible(true);
                confermaPasswordField.setVisible(true);
            }
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleSalvaPassword() {
        String pw = nuovaPasswordField.getText();
        String cpw = confermaPasswordField.getText();
        if (!pw.equals(cpw)) { AlertUtils.mostraErrore("Errore", "Le password non coincidono"); return; }
        String errore = ValidationUtils.validatePassword(pw);
        if (errore != null) { AlertUtils.mostraErrore("Formato non valido", errore); return; }
        try {
            if (recuperoPasswordCtrl != null) {
                recuperoPasswordCtrl.resetPassword(cfVerificato, pw);
                AlertUtils.mostraMessaggio("Recupero Password", "Password modificata con successo");
                SceneManager.switchTo("SchermataAccesso");
            }
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }
}
