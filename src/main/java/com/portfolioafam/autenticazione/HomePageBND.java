package com.portfolioafam.autenticazione;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.visualizzastudente.RicercaProfiloBND;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class HomePageBND {
    @FXML private TextField ricercaField;
    public HomePageBND() {}
    @FXML private void handleLogin() { SceneManager.switchTo("SchermataAccesso"); }
    @FXML private void handleCerca() {
        String q = ricercaField.getText();
        if (q != null && !q.trim().isEmpty()) {
            RicercaProfiloBND.setUltimaQuery(q);
        }
        SceneManager.switchToFresh("RicercaProfilo");
    }
    @FXML private void handleAccediSPID() { SceneManager.switchTo("SchermataSPID"); }
    @FXML private void handleAccediEIDAS() { SceneManager.switchTo("SchermataEIDAS"); }
}
