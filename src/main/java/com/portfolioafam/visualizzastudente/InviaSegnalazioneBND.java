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
    private static String preloadNome;
    private static Long preloadIdUtente, preloadIdContenuto;
    public InviaSegnalazioneBND() {}
    public static void preload(String nome, Long idUtente, Long idCont) {
        preloadNome = nome; preloadIdUtente = idUtente; preloadIdContenuto = idCont;
    }
    public void setInviaSegnalazioneCtrl(InviaSegnalazioneCTRL c) { this.inviaSegnalazioneCtrl = c; }
    @FXML private void initialize() {
        motivoCombo.getItems().addAll("CONTENUTO_INAPPROPRIATO","VIOLAZIONE_COPYRIGHT","SPAM","ALTRO");
        motivoCombo.setValue("CONTENUTO_INAPPROPRIATO");
        if (preloadNome != null) {
            contenutoLabel.setText("Contenuto: " + preloadNome);
            idUtenteEsterno = preloadIdUtente;
            idContenuto = preloadIdContenuto;
            preloadNome = null; preloadIdUtente = null; preloadIdContenuto = null;
        }
    }
    @FXML private void handleInvia() {
        try {
            inviaSegnalazioneCtrl.invia(idUtenteEsterno, idContenuto, motivoCombo.getValue(), descrizioneArea.getText());
            AlertUtils.mostraMessaggio("Segnalazione", "Segnalazione inviata con successo");
            SceneManager.switchTo("HomePage");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    @FXML private void handleIndietro() {
        SchermataProfiloStudenteBND.preloadData(
            SchermataProfiloStudenteBND.getCurrentCf(),
            SchermataProfiloStudenteBND.getCurrentNome(),
            SchermataProfiloStudenteBND.getCurrentCognome(),
            SchermataProfiloStudenteBND.getCurrentDatiAcc());
        SceneManager.switchToFresh("SchermataProfiloStudente");
    }
}
