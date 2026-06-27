package com.portfolioafam.accessoesterno;
import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.model.UtenteEsternoEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import com.portfolioafam.visualizzastudente.IdentificazioneCTRL;
import com.portfolioafam.visualizzastudente.SchermataProfiloStudenteBND;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
public class SchermataIdentificazioneBND {
    @FXML private TextField nomeField, cognomeField, emailField, professioneField;
    private IdentificazioneCTRL identificazioneCtrl;
    public SchermataIdentificazioneBND() {}
    public void setIdentificazioneCtrl(IdentificazioneCTRL c) { this.identificazioneCtrl = c; }
    @FXML private void handleConferma() {
        try {
            UtenteEsternoEntity u = identificazioneCtrl.identifica(nomeField.getText(), cognomeField.getText(),
                    emailField.getText(), professioneField.getText());
            SessionManager.getInstance().setUtenteEsternoId(u.getIdUtenteEsterno());
            AlertUtils.mostraMessaggio("Identificazione", "Identificazione riuscita");
            SchermataProfiloStudenteBND.preloadData(
                SchermataProfiloStudenteBND.getCurrentCf(),
                SchermataProfiloStudenteBND.getCurrentNome(),
                SchermataProfiloStudenteBND.getCurrentCognome(),
                SchermataProfiloStudenteBND.getCurrentDatiAcc());
            SceneManager.switchToFresh("SchermataProfiloStudente");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleAnnulla() { SceneManager.switchTo("HomePage"); }
}
