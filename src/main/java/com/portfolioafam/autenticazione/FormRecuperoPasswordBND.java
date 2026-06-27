package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.PasswordFieldUtils;
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
    private StudenteEntity studente;
    private SchermataVerifica2FABND verifica2faBnd;
    private StudenteRepository studenteRepository;
    public FormRecuperoPasswordBND() {}
    @FXML private void initialize() {
        PasswordFieldUtils.addToggle(nuovaPasswordField);
        PasswordFieldUtils.addToggle(confermaPasswordField);
    }
    public void setRecuperoPasswordCtrl(RecuperoPasswordCTRL c) { this.recuperoPasswordCtrl = c; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }
    public void setStudenteRepository(StudenteRepository r) { this.studenteRepository = r; }
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
                studente = s;
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
                if (verifica2faBnd != null) {
                    verifica2faBnd.setStudente(studente);
                    verifica2faBnd.setStudenteRepository(studenteRepository);
                    verifica2faBnd.setOnVerificaSuccesso(() -> {
                        AlertUtils.mostraMessaggio("Verifica 2FA", "Verifica riuscita");
                        SceneManager.switchTo("HomePage");
                    });
                }
                verifica2faBnd.setPaginaPrecedente("FormRecuperoPassword");
                SceneManager.switchTo("SchermataVerifica2FA");
            }
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }
}
