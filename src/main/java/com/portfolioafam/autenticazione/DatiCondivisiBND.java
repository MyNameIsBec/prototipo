package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DatiCondivisiBND {
    @FXML private Label datiLabel;
    @FXML private CheckBox consensoCheckBox;
    private Runnable onConsenso;

    public DatiCondivisiBND() {}
    public void setOnConsenso(Runnable r) { this.onConsenso = r; }

    public void setDatiStudente(StudenteEntity s, String provider) {
        datiLabel.setText(String.format(
            "Provider: %s\n\nNome: %s\nCognome: %s\nEmail: %s\nCodice Fiscale: %s",
            provider, s.getNome(), s.getCognome(), s.getEmail(), s.getCf()
        ));
    }

    @FXML
    private void handleAutorizza() {
        if (!consensoCheckBox.isSelected()) {
            AlertUtils.mostraErrore("Consenso", "Devi acconsentire al trattamento dei dati per proseguire.");
            return;
        }
        if (onConsenso != null) onConsenso.run();
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataAccesso"); }
}
