package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.service.AuthService;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.PasswordFieldUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.Optional;

public class SchermataEIDASBND {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ListView<String> providerListView;
    @FXML private VBox providerBox, credenzialiBox;
    @FXML private Label providerLabel, messaggioLabel;
    private StudenteRepository studenteRepository;
    private AuthService authService;
    private SchermataVerifica2FABND verifica2faBnd;
    private String providerSelezionato;

    public SchermataEIDASBND() {}
    public void setStudenteRepository(StudenteRepository r) { this.studenteRepository = r; }
    public void setAuthService(AuthService a) { this.authService = a; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }

    @FXML private void initialize() {
        PasswordFieldUtils.addToggle(passwordField);
        providerListView.getItems().addAll(
            "Italia (SPID)", "Germania (eID)", "Belgio (eID)",
            "Estonia (eID)", "Francia (FranceConnect)", "Spagna (Cl@ve)",
            "Paesi Bassi (DigiD)", "Portogallo (Chave Móvel)"
        );
    }

    @FXML private void handleSelezionaProvider() {
        String selected = providerListView.getSelectionModel().getSelectedItem();
        if (selected == null) { AlertUtils.mostraErrore("eIDAS", "Seleziona un paese"); return; }
        providerSelezionato = selected;
        providerLabel.setText(providerSelezionato);
        providerBox.setManaged(false);
        providerBox.setVisible(false);
        credenzialiBox.setManaged(true);
        credenzialiBox.setVisible(true);
    }

    @FXML private void handleTornaAProvider() {
        providerBox.setManaged(true);
        providerBox.setVisible(true);
        credenzialiBox.setManaged(false);
        credenzialiBox.setVisible(false);
    }

    @FXML
    private void handleAccedi() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email == null || email.isEmpty()) { AlertUtils.mostraErrore("eIDAS", "Inserisci l'email eIDAS"); return; }
        try {
            Optional<StudenteEntity> esistente = studenteRepository.findByEmail(email);
            if (esistente.isPresent()) {
                if (password == null || password.isEmpty()) {
                    AlertUtils.mostraErrore("eIDAS", "Inserisci la password");
                    return;
                }
                if (authService == null) { AlertUtils.mostraErrore("Errore", "Servizio login non disponibile"); return; }
                StudenteEntity s = authService.loginStudente(email, password);
                SessionManager.getInstance().avviaSessioneStudente(s);
                if (s.isPasswordTemporanea()) {
                    SceneManager.switchTo("ModificaPasswordPrimoAccesso");
                    return;
                }
                verifica2faBnd.setChiave(email);
                verifica2faBnd.setStudenteRepository(studenteRepository);
                verifica2faBnd.setStudente(s);
                verifica2faBnd.setOnVerificaSuccesso(() -> {
                    SceneManager.switchTo("SchermataProfilo");
                });
                verifica2faBnd.setPaginaPrecedente("SchermataAccesso");
                SceneManager.switchTo("SchermataVerifica2FA");
            } else {
                String nome = capitalizza(email.split("@")[0].replace(".", " "));
                String cognome = nome.contains(" ") ? nome.substring(nome.indexOf(" ")+1) : "Dupont";
                nome = nome.contains(" ") ? nome.substring(0, nome.indexOf(" ")) : nome;
                FormRegistrazioneBND.setPrefill(email, nome, cognome, "eIDAS (" + providerSelezionato + ")");
                SceneManager.switchTo("FormRegistrazione");
            }
        } catch (AuthService.AuthException e) {
            AlertUtils.mostraErrore("eIDAS", "Credenziali non valide");
        } catch (SQLException e) {
            AlertUtils.mostraErrore("Errore", "Errore di connessione al database");
        }
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }

    private static String capitalizza(String s) {
        if (s == null || s.isEmpty()) return "";
        String[] parti = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parti) {
            if (!p.isEmpty()) {
                sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1).toLowerCase());
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
}
