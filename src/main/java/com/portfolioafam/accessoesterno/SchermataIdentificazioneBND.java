package com.portfolioafam.accessoesterno;
import com.portfolioafam.model.UtenteEsternoEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.visualizzastudente.IdentificazioneCTRL;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class SchermataIdentificazioneBND {
    @FXML private TextField nomeField, cognomeField, emailField, professioneField;
    private IdentificazioneCTRL identificazioneCtrl;
    public SchermataIdentificazioneBND() {}
    public void setIdentificazioneCtrl(IdentificazioneCTRL c) { this.identificazioneCtrl = c; }
    @FXML private void handleConferma() {
        try {
            UtenteEsternoEntity u = identificazioneCtrl.identifica(nomeField.getText(), cognomeField.getText(),
                    emailField.getText(), professioneField.getText());
            AlertUtils.mostraMessaggio("Identificazione", "Identificazione riuscita");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
}
