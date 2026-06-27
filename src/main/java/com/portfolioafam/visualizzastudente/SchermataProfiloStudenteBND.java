package com.portfolioafam.visualizzastudente;

import com.portfolioafam.model.*;
import com.portfolioafam.gestionecontenuti.ScaricaContenutoCTRL;
import com.portfolioafam.repository.*;
import com.portfolioafam.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;

public class SchermataProfiloStudenteBND {
    @FXML private VBox contentArea;
    private StudenteRepository studenteRepository;
    private CartellaRepository cartellaRepository;
    private ContenutoRepository contenutoRepository;
    private ScaricaContenutoCTRL scaricaContenutoCtrl;

    private static String cfToLoad, nomeToLoad, cognomeToLoad, datiAccToLoad;
    private static String currentCf, currentNome, currentCognome, currentDatiAcc;
    private static Long pendingContenutoIdForDownload;

    private String cf, nome, cognome, datiAcc;
    private boolean inFolderMode;
    private long folderId;

    public SchermataProfiloStudenteBND() {}

    public static void preloadData(String cf, String nome, String cognome, String datiAcc) {
        cfToLoad = cf; nomeToLoad = nome; cognomeToLoad = cognome; datiAccToLoad = datiAcc;
        currentCf = cf; currentNome = nome; currentCognome = cognome; currentDatiAcc = datiAcc;
    }
    public static String getCurrentCf() { return currentCf; }
    public static String getCurrentNome() { return currentNome; }
    public static String getCurrentCognome() { return currentCognome; }
    public static String getCurrentDatiAcc() { return currentDatiAcc; }

    public void setRepositories(StudenteRepository sr, CartellaRepository cr, ContenutoRepository cor) {
        this.studenteRepository = sr; this.cartellaRepository = cr; this.contenutoRepository = cor;
    }
    public void setScaricaContenutoCtrl(ScaricaContenutoCTRL s) { this.scaricaContenutoCtrl = s; }

    @FXML private void initialize() {
        if (nomeToLoad != null) {
            cf = cfToLoad; nome = nomeToLoad; cognome = cognomeToLoad; datiAcc = datiAccToLoad;
            cfToLoad = null; nomeToLoad = null; cognomeToLoad = null; datiAccToLoad = null;
            caricaProfiloCompleto();
        }
    }

    private void caricaProfiloCompleto() {
        inFolderMode = false;
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label(nome + " " + cognome) {{
            setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        }});
        contentArea.getChildren().add(new Label(datiAcc != null ? datiAcc : "") {{
            setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
            setWrapText(true);
        }});
        try {
            if (cartellaRepository != null) {
                Label cartelleTitle = new Label("Cartelle pubbliche");
                cartelleTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
                contentArea.getChildren().add(cartelleTitle);
                FlowPane fp = new FlowPane(15, 15);
                for (CartellaEntity c : cartellaRepository.findByCf(cf)) {
                    if (!"PUBBLICO".equals(c.getPrivacy())) continue;
                    fp.getChildren().add(creaCardCartella(c));
                }
                contentArea.getChildren().add(fp);
            }
            if (contenutoRepository != null) {
                Label contTitle = new Label("Contenuti");
                contTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
                contentArea.getChildren().add(contTitle);
                Label hint = new Label("Clicca un contenuto per visualizzarlo.");
                hint.setStyle("-fx-font-size: 12px; -fx-text-fill: #94a3b8; -fx-font-style: italic;");
                contentArea.getChildren().add(hint);
                FlowPane fp = new FlowPane(15, 15);
                for (ContenutoEntity c : contenutoRepository.findByCf(cf)) {
                    if (c.getIdCartella() != null) continue;
                    if (!"PUBBLICO".equals(c.getPrivacy())) continue;
                    fp.getChildren().add(creaCardContenuto(c));
                }
                contentArea.getChildren().add(fp);
            }
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Errore caricamento profilo"); }
    }

    private void caricaContenutiCartella(long idCartella, String nomeCartella) {
        inFolderMode = true;
        folderId = idCartella;
        contentArea.getChildren().clear();
        Button backBtn = new Button("← Torna al profilo");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6366f1; -fx-border-color: #6366f1; -fx-border-radius: 4; -fx-padding: 6 14; -fx-font-size: 13px; -fx-cursor: hand;");
        backBtn.setOnAction(e -> caricaProfiloCompleto());
        contentArea.getChildren().add(backBtn);
        contentArea.getChildren().add(new Label(nomeCartella) {{
            setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        }});
        try {
            FlowPane fp = new FlowPane(15, 15);
            for (ContenutoEntity c : contenutoRepository.findByCartella(idCartella)) {
                if (!"PUBBLICO".equals(c.getPrivacy())) continue;
                fp.getChildren().add(creaCardContenuto(c));
            }
            contentArea.getChildren().add(fp);
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Errore caricamento cartella"); }
    }

    private VBox creaCardCartella(CartellaEntity c) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-min-width: 160; -fx-cursor: hand;");
        card.getChildren().add(new Label("\uD83D\uDCC1") {{ setStyle("-fx-font-size: 28px;"); }});
        card.getChildren().add(new Label(c.getNomeCartella()) {{ setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"); }});
        Button apri = new Button("APRI");
        apri.setStyle("-fx-background-color: #6366f1; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 6 16; -fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand;");
        apri.setOnAction(e -> caricaContenutiCartella(c.getIdCartella(), c.getNomeCartella()));
        card.getChildren().add(apri);
        return card;
    }

    private VBox creaCardContenuto(ContenutoEntity c) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-min-width: 160; -fx-cursor: hand;");
        card.setOnMouseClicked(e -> apriContenuto(c));
        String icona = c.getTipo() != null && c.getTipo().contains("PDF") ? "\uD83D\uDCC4" :
                        c.getTipo() != null && c.getTipo().contains("IMAGE") ? "\uD83D\uDDBC" :
                        c.getTipo() != null && c.getTipo().contains("IMMAGINE") ? "\uD83D\uDDBC" :
                        c.getTipo() != null && c.getTipo().contains("AUDIO") ? "\uD83C\uDFB5" :
                        c.getTipo() != null && c.getTipo().contains("VIDEO") ? "\uD83C\uDFA5" : "\uD83D\uDCC4";
        card.getChildren().add(new Label(icona) {{ setStyle("-fx-font-size: 28px;"); }});
        card.getChildren().add(new Label(c.getNome()) {{ setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;"); }});
        Label tipoLabel = new Label(c.getTipo() != null ? c.getTipo() : "");
        tipoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8;");
        card.getChildren().add(tipoLabel);
        MenuButton menu = new MenuButton("\u22EE");
        menu.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-cursor: hand;");
        MenuItem scarica = new MenuItem("Scarica");
        scarica.setOnAction(e -> scaricaContenuto(c));
        MenuItem segnala = new MenuItem("Segnala");
        segnala.setOnAction(e -> segnalaContenuto(c));
        menu.getItems().addAll(scarica, segnala);
        card.getChildren().add(menu);
        return card;
    }

    private void apriContenuto(ContenutoEntity c) {
        SchermataVisualizzaContenutoBND.preload(c, contenutoRepository);
        SceneManager.switchToFresh("SchermataVisualizzaContenuto");
    }

    private void scaricaContenuto(ContenutoEntity c) {
        if (SessionManager.getInstance().getUtenteEsternoId() == null) {
            pendingContenutoIdForDownload = c.getIdContenuto();
            SceneManager.switchTo("SchermataIdentificazione");
            return;
        }
        try {
            if (scaricaContenutoCtrl != null) {
                byte[] data = scaricaContenutoCtrl.scarica(c.getIdContenuto());
                if (data == null || data.length == 0) {
                    AlertUtils.mostraErrore("Scarica", "File non disponibile");
                    return;
                }
                FileChooser fc = new FileChooser();
                fc.setInitialFileName(c.getNome());
                File f = fc.showSaveDialog(null);
                if (f != null) {
                    Files.write(f.toPath(), data);
                    AlertUtils.mostraMessaggio("Scarica", "File salvato: " + f.getName());
                }
            }
        } catch (Exception e) { AlertUtils.mostraErrore("Scarica", e.getMessage()); }
    }

    private void segnalaContenuto(ContenutoEntity c) {
        InviaSegnalazioneBND.preload(c.getNome(), SessionManager.getInstance().getUtenteEsternoId(), c.getIdContenuto());
        SceneManager.switchToFresh("InviaSegnalazione");
    }

    public static void eseguiPendingDownload(Long idContenuto) {
        pendingContenutoIdForDownload = idContenuto;
    }

    public static Long getPendingDownloadId() {
        Long id = pendingContenutoIdForDownload;
        pendingContenutoIdForDownload = null;
        return id;
    }

    @FXML private void handleIndietro() {
        if (inFolderMode) {
            caricaProfiloCompleto();
        } else {
            SceneManager.switchTo("HomePage");
        }
    }
}
