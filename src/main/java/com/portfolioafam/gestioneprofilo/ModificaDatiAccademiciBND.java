package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ModificaDatiAccademiciBND {

    @FXML private TextArea datiAccademiciArea;

    private ModificaDatiAccademiciCTRL modificaDatiAccademiciCtrl;

    public ModificaDatiAccademiciBND() {
    }

    public void setModificaDatiAccademiciCtrl(ModificaDatiAccademiciCTRL ctrl) {
        this.modificaDatiAccademiciCtrl = ctrl;
    }

    @FXML
    private void handleSalva() {
        String nuoviDati = datiAccademiciArea.getText();
        try {
            if (modificaDatiAccademiciCtrl != null) {
                modificaDatiAccademiciCtrl.modificaDatiAccademici(nuoviDati);
                AlertUtils.mostraMessaggio("Modifica dati accademici", "Dati accademici aggiornati con successo");
                SceneManager.switchTo("SchermataProfilo");
            }
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", e.getMessage());
        }
    }

    @FXML
    private void handleIndietro() {
        SceneManager.switchTo("SchermataProfilo");
    }
}
