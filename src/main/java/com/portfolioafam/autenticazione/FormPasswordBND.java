package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class FormPasswordBND {
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confermaPasswordField;
    private RegistrazioneCTRL registrazioneCtrl;
    private StudenteEntity studentePending;
    public FormPasswordBND() {}
    public void setRegistrazioneCtrl(RegistrazioneCTRL c) { this.registrazioneCtrl = c; }
    public void setStudentePending(StudenteEntity s) { this.studentePending = s; }
    @FXML private void handleConferma() {
        String pw = passwordField.getText();
        String cpw = confermaPasswordField.getText();
        if (!pw.equals(cpw)) { AlertUtils.mostraErrore("Errore", "Le password non coincidono"); return; }
        String errore = ValidationUtils.validatePassword(pw);
        if (errore != null) { AlertUtils.mostraErrore("Formato non valido", errore); return; }
        try {
            if (studentePending != null) {
                studentePending.setHashPassword(pw);
                registrazioneCtrl.registra(studentePending);
                AlertUtils.mostraMessaggio("2FA", "Attivazione riuscita");
                SceneManager.switchTo("SchermataAttiva2fa");
            }
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
}
