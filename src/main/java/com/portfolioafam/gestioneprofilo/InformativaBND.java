package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InformativaBND {

    @FXML private TextArea informativaArea;

    private EliminaAccountCTRL eliminaAccountCtrl;

    public InformativaBND() {
    }

    public void setEliminaAccountCtrl(EliminaAccountCTRL ctrl) {
        this.eliminaAccountCtrl = ctrl;
    }

    @FXML
    private void initialize() {
        informativaArea.setText(
            "INFORMATIVA SULL'ELIMINAZIONE DELL'ACCOUNT\n\n"
            + "Conformemente all'Art. 17 del GDPR (diritto all'oblio),\n"
            + "hai il diritto di richiedere la cancellazione dei tuoi dati personali.\n\n"
            + "- I tuoi dati verranno conservati per 14 giorni prima dell'eliminazione definitiva\n"
            + "- Durante questo periodo potrai recuperare il tuo account\n"
            + "- Hai diritto alla portabilità dei tuoi dati in formato interoperabile\n"
            + "- I contenuti condivisi pubblicamente potrebbero rimanere accessibili\n\n"
            + "Procedendo, accetti queste condizioni."
        );
    }

    @FXML
    private void handleAccetta() {
        SceneManager.switchTo("ConfermaEliminazione");
    }

    @FXML
    private void handleIndietro() {
        SceneManager.switchTo("SchermataProfilo");
    }
}
