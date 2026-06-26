package com.portfolioafam.gestioneprivacy;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataVisibilitaProfiloBND {
    @FXML private RadioButton pubblicoRadio;
    @FXML private RadioButton privatoRadio;
    private ModificaVisibilitaCTRL modificaVisibilitaCtrl;
    public SchermataVisibilitaProfiloBND() {}
    public void setModificaVisibilitaCtrl(ModificaVisibilitaCTRL c) { this.modificaVisibilitaCtrl = c; }
    @FXML private void handleSalva() {
        try {
            if (modificaVisibilitaCtrl != null) {
                modificaVisibilitaCtrl.modificaVisibilita(pubblicoRadio.isSelected() ? "PUBBLICO" : "PRIVATO");
                AlertUtils.mostraMessaggio("Visibilità", "Visibilità profilo aggiornata");
                SceneManager.switchTo("SchermataProfilo");
            }
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataProfilo"); }
}
