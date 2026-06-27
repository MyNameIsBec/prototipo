package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.service.EmailService;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataVerifica2FABND {
    @FXML private TextField email2faField;
    @FXML private TextField otpField;
    @FXML private Button inviaButton;
    @FXML private Button verificaButton;
    @FXML private Label messaggioLabel;
    private Verifica2faCTRL verifica2faCtrl;
    private String chiave;
    private Runnable onVerificaSuccesso;
    private StudenteRepository studenteRepository;
    private StudenteEntity studente;
    private EmailService emailService;
    private String paginaPrecedente = "SchermataAccesso";

    public SchermataVerifica2FABND() {}
    public void setVerifica2faCtrl(Verifica2faCTRL c) { this.verifica2faCtrl = c; }
    public void setOnVerificaSuccesso(Runnable r) { this.onVerificaSuccesso = r; }
    public Runnable getOnVerificaSuccesso() { return onVerificaSuccesso; }
    public void setStudenteRepository(StudenteRepository r) { this.studenteRepository = r; }
    public void setStudente(StudenteEntity s) { this.studente = s; }
    public void setChiave(String chiave) { this.chiave = chiave; }
    public void setEmailService(EmailService e) { this.emailService = e; }
    public void setPaginaPrecedente(String p) { this.paginaPrecedente = p; }

    @FXML private void handleIndietro() { SceneManager.switchTo(paginaPrecedente); }
    @FXML private void handleInvia() {
        String email = email2faField.getText();
        if (email == null || email.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Inserisci l'email per la verifica");
            return;
        }
        if (studenteRepository != null && studente != null) {
            try {
                StudenteEntity s = studenteRepository.findByCf(studente.getCf()).orElse(null);
                if (s == null || !email.equalsIgnoreCase(s.getEmail2fa())) {
                    AlertUtils.mostraErrore("Errore", "Email non corretta");
                    return;
                }
            } catch (Exception e) {
                AlertUtils.mostraErrore("Errore", "Errore durante la verifica dell'email");
                return;
            }
        }
        this.chiave = email;
        if (verifica2faCtrl != null) {
            String otp = verifica2faCtrl.generaEOttieniOTP(chiave);
            if (emailService != null) {
                try {
                    emailService.inviaOTP(email, otp);
                } catch (Exception e) {
                    AlertUtils.mostraErrore("Errore", "Impossibile inviare l'email di verifica");
                    return;
                }
            }
            AlertUtils.mostraMessaggio("OTP", "OTP inviata");
        }
    }

    @FXML private void handleVerifica() {
        String otp = otpField.getText();
        if (otp == null || otp.isEmpty()) {
            AlertUtils.mostraErrore("Errore", "Inserisci il codice di verifica");
            return;
        }
        if (verifica2faCtrl != null && chiave != null) {
            boolean ok = verifica2faCtrl.verificaOTP(chiave, otp);
            if (ok) {
                messaggioLabel.setText("Verifica riuscita");
                if (onVerificaSuccesso != null) onVerificaSuccesso.run();
            } else {
                messaggioLabel.setText("Codice errato");
                String newOtp = verifica2faCtrl.generaEOttieniOTP(chiave);
                if (emailService != null) {
                    try {
                        emailService.inviaOTP(chiave, newOtp);
                    } catch (Exception e) { }
                }
                AlertUtils.mostraMessaggio("OTP", "OTP reinviata");
            }
        }
    }
}
