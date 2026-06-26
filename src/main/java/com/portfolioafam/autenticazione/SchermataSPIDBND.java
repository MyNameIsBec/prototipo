package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SchermataSPIDBND {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    private DatiCondivisiBND datiCondivisiBnd;

    public SchermataSPIDBND() {}
    public void setDatiCondivisiBnd(DatiCondivisiBND b) { this.datiCondivisiBnd = b; }

    @FXML
    private void handleAccedi() {
        String email = emailField.getText();
        if (email == null || email.isEmpty()) { AlertUtils.mostraErrore("SPID", "Inserisci l'email SPID"); return; }
        String nome = capitalizza(email.split("@")[0].replace(".", " "));
        String cognome = nome.contains(" ") ? nome.substring(nome.indexOf(" ")+1) : "Rossi";
        nome = nome.contains(" ") ? nome.substring(0, nome.indexOf(" ")) : nome;

        StudenteEntity s = new StudenteEntity("RSSMRA85M01A271X", nome, cognome, email, "", email, "PRIVATO");
        s.setDatiAccademici("Pianoforte - Conservatorio - A.A. 2025/26");

        if (datiCondivisiBnd != null) {
            datiCondivisiBnd.setDatiStudente(s, "SPID");
            datiCondivisiBnd.setOnConsenso(() -> {
                SessionManager.getInstance().avviaSessioneStudente(s);
                AlertUtils.mostraMessaggio("Accesso SPID", "Accesso effettuato con successo");
                SceneManager.switchTo("SchermataProfilo");
            });
        }
        SceneManager.switchTo("DatiCondivisi");
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }

    private static String capitalizza(String s) {
        if (s == null || s.isEmpty()) return "";
        String[] parti = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parti) {
            if (!p.isEmpty()) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}
