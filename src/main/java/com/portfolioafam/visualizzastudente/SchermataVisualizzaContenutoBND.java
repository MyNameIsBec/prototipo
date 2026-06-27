package com.portfolioafam.visualizzastudente;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import com.portfolioafam.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;

public class SchermataVisualizzaContenutoBND {
    @FXML private Label nomeContenutoBarLabel, tipoLabel, dimensioneLabel, placeholderLabel;
    @FXML private StackPane previewPane;
    @FXML private ImageView imagePreview;

    private static ContenutoEntity contenutoToLoad;
    private static ContenutoRepository repoToLoad;
    private ContenutoEntity contenuto;
    private ContenutoRepository contenutoRepository;

    public SchermataVisualizzaContenutoBND() {}

    public static void preload(ContenutoEntity c, ContenutoRepository r) {
        contenutoToLoad = c; repoToLoad = r;
    }

    @FXML private void initialize() {
        if (contenutoToLoad != null) {
            contenuto = contenutoToLoad;
            contenutoRepository = repoToLoad;
            contenutoToLoad = null; repoToLoad = null;
            mostraContenuto();
        }
    }

    private void mostraContenuto() {
        nomeContenutoBarLabel.setText(contenuto.getNome());
        tipoLabel.setText("Tipo: " + (contenuto.getTipo() != null ? contenuto.getTipo() : "N/D"));
        long kb = (contenuto.getDimensione() != null ? contenuto.getDimensione() : 0) / 1024;
        dimensioneLabel.setText("Dimensione: " + kb + " KB");
        byte[] data = contenuto.getFileDati();
        if (data != null && data.length > 0) {
            String tipo = contenuto.getTipo() != null ? contenuto.getTipo().toUpperCase() : "";
            if (tipo.contains("IMAGE") || tipo.contains("IMMAGINE") || tipo.contains("JPG") || tipo.contains("PNG")) {
                Image img = new Image(new ByteArrayInputStream(data));
                if (!img.isError()) {
                    imagePreview.setImage(img);
                    imagePreview.setVisible(true);
                    placeholderLabel.setVisible(false);
                }
            }
        }
    }

    @FXML private void handleApri() {
        byte[] data = contenuto.getFileDati();
        if (data == null || data.length == 0) {
            AlertUtils.mostraErrore("Apri", "File non disponibile");
            return;
        }
        try {
            File temp = File.createTempFile("portfolio_", "_" + contenuto.getNome());
            temp.deleteOnExit();
            Files.write(temp.toPath(), data);
            java.awt.Desktop.getDesktop().open(temp);
        } catch (Exception e) {
            AlertUtils.mostraErrore("Apri", e.getMessage());
        }
    }

    @FXML private void handleScarica() {
        if (SessionManager.getInstance().getUtenteEsternoId() == null) {
            AlertUtils.mostraErrore("Scarica", "Devi prima identificarti (pulsante IDENTIFICAZIONE)");
            return;
        }
        byte[] data = contenuto.getFileDati();
        if (data == null || data.length == 0) {
            AlertUtils.mostraErrore("Scarica", "File non disponibile");
            return;
        }
        try {
            FileChooser fc = new FileChooser();
            fc.setInitialFileName(contenuto.getNome());
            File f = fc.showSaveDialog(null);
            if (f != null) {
                Files.write(f.toPath(), data);
                AlertUtils.mostraMessaggio("Scarica", "File salvato: " + f.getName());
            }
        } catch (Exception e) {
            AlertUtils.mostraErrore("Scarica", e.getMessage());
        }
    }

    @FXML private void handleValuta() {
        if (SessionManager.getInstance().getUtenteEsternoId() == null) {
            AlertUtils.mostraErrore("Valuta", "Devi prima identificarti");
            return;
        }
        TextInputDialog d = new TextInputDialog("5");
        d.setTitle("Valuta contenuto");
        d.setHeaderText("Valuta: " + contenuto.getNome());
        d.setContentText("Voto (1-10):");
        d.showAndWait().ifPresent(v -> {
            try {
                int voto = Integer.parseInt(v);
                if (voto < 1 || voto > 10) { AlertUtils.mostraErrore("Errore", "Il voto deve essere tra 1 e 10"); return; }
                AlertUtils.mostraMessaggio("Valutazione", "Valutazione registrata: " + voto);
            } catch (NumberFormatException ex) {
                AlertUtils.mostraErrore("Errore", "Inserisci un numero valido");
            }
        });
    }

    @FXML private void handleSegnala() {
        InviaSegnalazioneBND.preload(contenuto.getNome(), SessionManager.getInstance().getUtenteEsternoId(), contenuto.getIdContenuto());
        SceneManager.switchToFresh("InviaSegnalazione");
    }

    @FXML private void handleChiudi() {
        SchermataProfiloStudenteBND.preloadData(
            SchermataProfiloStudenteBND.getCurrentCf(),
            SchermataProfiloStudenteBND.getCurrentNome(),
            SchermataProfiloStudenteBND.getCurrentCognome(),
            SchermataProfiloStudenteBND.getCurrentDatiAcc());
        SceneManager.switchToFresh("SchermataProfiloStudente");
    }
}
