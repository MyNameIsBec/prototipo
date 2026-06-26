package com.portfolioafam.autenticazione;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;

public class SchermataVerificaOcrBND {
    @FXML private Label statoLabel;
    @FXML private Button caricaFronteButton, caricaRetroButton, scansionaButton;
    private VerificaOCRCTRL verificaOcrCtrl;
    private byte[] fronteData, retroData;
    public SchermataVerificaOcrBND() { this.verificaOcrCtrl = new VerificaOCRCTRL(); }
    @FXML private void handleCaricaFronte() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleziona fronte tessera sanitaria");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try { fronteData = Files.readAllBytes(file.toPath()); caricaFronteButton.setText("Fronte caricato"); }
            catch (IOException e) { AlertUtils.mostraErrore("Errore", "Impossibile leggere il file"); }
        }
    }
    @FXML private void handleCaricaRetro() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleziona retro tessera sanitaria");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try { retroData = Files.readAllBytes(file.toPath()); caricaRetroButton.setText("Retro caricato"); }
            catch (IOException e) { AlertUtils.mostraErrore("Errore", "Impossibile leggere il file"); }
        }
    }
    @FXML private void handleScansiona() {
        if (fronteData == null || retroData == null) {
            AlertUtils.mostraErrore("Errore", "Carica fronte e retro della tessera sanitaria"); return;
        }
        try {
            boolean ok = verificaOcrCtrl.verificaTessera(fronteData, "", "", "");
            if (ok) {
                statoLabel.setText("Verifica riuscita");
                AlertUtils.mostraMessaggio("Verifica OCR", "Verifica riuscita");
                SceneManager.switchTo("FormPassword");
            } else {
                statoLabel.setText("Verifica fallita");
                AlertUtils.mostraErrore("Verifica OCR", "Verifica fallita");
            }
        } catch (IOException e) { AlertUtils.mostraErrore("Errore", "Errore durante la verifica"); }
    }
}
