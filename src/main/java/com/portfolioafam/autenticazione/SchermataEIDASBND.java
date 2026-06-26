package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SchermataEIDASBND {
    @FXML private TextField emailField;
    private DatiCondivisiBND datiCondivisiBnd;

    public SchermataEIDASBND() {}
    public void setDatiCondivisiBnd(DatiCondivisiBND b) { this.datiCondivisiBnd = b; }

    @FXML
    private void handleAccedi() {
        String email = emailField.getText();
        if (email == null || email.isEmpty()) { AlertUtils.mostraErrore("eIDAS", "Inserisci l'email eIDAS"); return; }
        StudenteEntity s = new StudenteEntity("DUPJNT85M01Z110A", "Jean", "Dupont", email, "", email, "PRIVATO");
        s.setDatiAccademici("Violino - Conservatoire de Paris - A.A. 2025/26");

        if (datiCondivisiBnd != null) {
            datiCondivisiBnd.setDatiStudente(s, "eIDAS");
            datiCondivisiBnd.setOnConsenso(() -> {
                SessionManager.getInstance().avviaSessioneStudente(s);
                AlertUtils.mostraMessaggio("Accesso eIDAS", "Accesso effettuato con successo");
                SceneManager.switchTo("SchermataProfilo");
            });
        }
        SceneManager.switchTo("DatiCondivisi");
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }
}
