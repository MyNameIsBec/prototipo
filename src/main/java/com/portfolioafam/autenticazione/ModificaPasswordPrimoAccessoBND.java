package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.PasswordFieldUtils;
import com.portfolioafam.util.PasswordUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public class ModificaPasswordPrimoAccessoBND {
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confermaPasswordField;
    @FXML private Label messaggioLabel;
    private StudenteEntity studente;
    private StudenteRepository studenteRepository;
    private SchermataVerifica2FABND verifica2faBnd;

    public ModificaPasswordPrimoAccessoBND() {}
    @FXML private void initialize() {
        PasswordFieldUtils.addToggle(passwordField);
        PasswordFieldUtils.addToggle(confermaPasswordField);
    }
    public void setStudente(StudenteEntity s) { this.studente = s; }
    public void setStudenteRepository(StudenteRepository r) { this.studenteRepository = r; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }

    @FXML private void handleConferma() {
        String pw = passwordField.getText();
        String cpw = confermaPasswordField.getText();
        if (!pw.equals(cpw)) { AlertUtils.mostraErrore("Errore", "Le password non coincidono"); return; }
        String errore = ValidationUtils.validatePassword(pw);
        if (errore != null) { AlertUtils.mostraErrore("Formato non valido", errore); return; }
        try {
            if (studente != null && studenteRepository != null) {
                studente.setHashPassword(PasswordUtils.hashPassword(pw));
                studente.setPasswordTemporanea(false);
                studenteRepository.save(studente);
                if (verifica2faBnd != null) {
                    verifica2faBnd.setStudente(studente);
                    verifica2faBnd.setStudenteRepository(studenteRepository);
                    verifica2faBnd.setOnVerificaSuccesso(() -> SceneManager.switchTo("SchermataProfilo"));
                }
                verifica2faBnd.setPaginaPrecedente("ModificaPasswordPrimoAccesso");
                SceneManager.switchTo("SchermataVerifica2FA");
            }
        } catch (SQLException e) {
            AlertUtils.mostraErrore("Errore", "Errore durante il salvataggio della password");
        }
    }
}
