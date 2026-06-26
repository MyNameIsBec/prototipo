package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class FormRegistrazioneBND {
    @FXML private TextField nomeField, cognomeField, cfField, dataNascitaField, luogoNascitaField, emailField, telefonoField;
    @FXML private TextArea datiAccademiciArea;
    @FXML private CheckBox gdprCheckBox;
    private RegistrazioneCTRL registrazioneCtrl;
    private FormPasswordBND formPasswordBnd;
    public FormRegistrazioneBND() {}
    public void setRegistrazioneCtrl(RegistrazioneCTRL c) { this.registrazioneCtrl = c; }
    public void setFormPasswordBnd(FormPasswordBND b) { this.formPasswordBnd = b; }
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
            SceneManager.switchTo("SchermataVerificaOcr");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", "Connessione al server interrotta"); }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }
}
