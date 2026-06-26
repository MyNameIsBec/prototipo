package com.portfolioafam.visualizzastudente;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class InviaSegnalazioneBND {
    @FXML private Label contenutoLabel;
    @FXML private ComboBox<String> motivoCombo;
    @FXML private TextArea descrizioneArea;
    private InviaSegnalazioneCTRL inviaSegnalazioneCtrl;
    private Long idUtenteEsterno, idContenuto;
    public InviaSegnalazioneBND() {}
    public void setInviaSegnalazioneCtrl(InviaSegnalazioneCTRL c) { this.inviaSegnalazioneCtrl = c; }
    public void setContenuto(String nome, Long idUtente, Long idCont) { contenutoLabel.setText("Contenuto: " + nome); this.idUtenteEsterno = idUtente; this.idContenuto = idCont; }
    @FXML private void initialize() {
        motivoCombo.getItems().addAll("CONTENUTO_INAPPROPRIATO","VIOLAZIONE_COPYRIGHT","SPAM","ALTRO");
        motivoCombo.setValue("CONTENUTO_INAPPROPRIATO");
    }
    @FXML private void handleInvia() {
        try {
            inviaSegnalazioneCtrl.invia(idUtenteEsterno, idContenuto, motivoCombo.getValue(), descrizioneArea.getText());
            AlertUtils.mostraMessaggio("Segnalazione", "Segnalazione inviata con successo");
            SceneManager.switchTo("HomePage");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataProfiloStudente"); }
}
