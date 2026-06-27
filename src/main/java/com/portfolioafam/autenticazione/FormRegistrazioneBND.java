package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class FormRegistrazioneBND {
    @FXML private TextField nomeField, cognomeField, cfField, luogoNascitaField, emailField, telefonoField;
    @FXML private DatePicker dataNascitaPicker;
    @FXML private TextArea datiAccademiciArea;
    @FXML private CheckBox gdprCheckBox;
    @FXML private Button registratiButton;
    private RegistrazioneCTRL registrazioneCtrl;
    private FormPasswordBND formPasswordBnd;
    private static String prefillEmail, prefillNome, prefillCognome, prefillDatiAccademici;

    public FormRegistrazioneBND() {}

    public static void setPrefill(String email, String nome, String cognome, String datiAccademici) {
        prefillEmail = email;
        prefillNome = nome;
        prefillCognome = cognome;
        prefillDatiAccademici = datiAccademici;
    }

    public void setRegistrazioneCtrl(RegistrazioneCTRL c) { this.registrazioneCtrl = c; }
    public void setFormPasswordBnd(FormPasswordBND b) { this.formPasswordBnd = b; }

    @FXML private void initialize() {
        if (prefillEmail != null) {
            emailField.setText(prefillEmail);
            nomeField.setText(prefillNome);
            cognomeField.setText(prefillCognome);
            if (prefillDatiAccademici != null) datiAccademiciArea.setText(prefillDatiAccademici);
            prefillEmail = null; prefillNome = null; prefillCognome = null; prefillDatiAccademici = null;
        }
    }

    @FXML private void handleRegistrati() {
        String nome = nomeField.getText(), cognome = cognomeField.getText(), cf = cfField.getText().toUpperCase(), email = emailField.getText();
        if (!ValidationUtils.isNotEmpty(nome) || !ValidationUtils.isNotEmpty(cognome) || !ValidationUtils.isNotEmpty(cf) || !ValidationUtils.isNotEmpty(email)) {
            AlertUtils.mostraErrore("Errore", "Compila tutti i campi obbligatori"); return;
        }
        if (!ValidationUtils.isValidEmail(email)) { AlertUtils.mostraErrore("Errore", "Formato email non valido"); return; }
        if (!ValidationUtils.isValidCF(cf)) { AlertUtils.mostraErrore("Errore", "Formato Codice Fiscale non valido"); return; }
        if (!gdprCheckBox.isSelected()) { AlertUtils.mostraErrore("Errore", "Devi acconsentire al trattamento dei dati"); return; }
        try {
            if (registrazioneCtrl.isCfRegistrato(cf)) { AlertUtils.mostraErrore("Errore", "Questo utente risulta già registrato"); return; }
            StudenteEntity s = new StudenteEntity(cf, nome, cognome, email, "", "", "PRIVATO");
            s.setTelefono(telefonoField.getText());
            s.setDatiAccademici(datiAccademiciArea.getText());
            if (formPasswordBnd != null) formPasswordBnd.setStudentePending(s);
            SchermataVerificaOcrBND.setStudentePending(s);
            SceneManager.switchTo("SchermataVerificaOcr");
        } catch (Exception e) { e.printStackTrace(); AlertUtils.mostraErrore("Errore", "Connessione al server interrotta: " + e.getMessage()); }
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }
}
