package com.portfolioafam.autenticazione;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class SchermataVerificaOcrBND {
    @FXML private Label fronteLabel, retroLabel, statoLabel;
    @FXML private Button scansionaButton;
    private VerificaOCRCTRL verificaOcrCtrl;
    private byte[] fronteData, retroData;
    private static StudenteEntity studentePending;
    private static final long MAX_SIZE = 5L * 1024 * 1024;

    public SchermataVerificaOcrBND() { this.verificaOcrCtrl = new VerificaOCRCTRL(); }

    public static void setStudentePending(StudenteEntity s) { studentePending = s; }

    @FXML private void handleIndietro() {
        studentePending = null;
        SceneManager.switchTo("FormRegistrazione");
    }

    @FXML private void handleScansiona() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleziona fronte e retro della tessera sanitaria");
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Immagini", "*.png", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("PNG", "*.png"),
            new FileChooser.ExtensionFilter("JPG/JPEG", "*.jpg", "*.jpeg"));
        List<File> files = chooser.showOpenMultipleDialog(null);
        if (files == null || files.isEmpty()) return;
        if (files.size() > 2) {
            AlertUtils.mostraErrore("Errore", "Seleziona al massimo 2 file (fronte e retro)");
            return;
        }
        try {
            fronteLabel.setText("Nessun file caricato (fronte)");
            retroLabel.setText("Nessun file caricato (retro)");
            fronteLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #999;");
            retroLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #999;");
            for (File f : files) {
                if (f.length() > MAX_SIZE) {
                    AlertUtils.mostraErrore("File troppo grande", "Il file " + f.getName() + " supera i 5MB");
                    return;
                }
            }
            byte[] first = Files.readAllBytes(files.get(0).toPath());
            byte[] second = files.size() > 1 ? Files.readAllBytes(files.get(1).toPath()) : null;
            fronteData = first;
            retroData = second;
            fronteLabel.setText("Fronte: " + files.get(0).getName() + " (" + (files.get(0).length() / 1024) + "KB)");
            fronteLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");
            if (retroData != null) {
                retroLabel.setText("Retro: " + files.get(1).getName() + " (" + (files.get(1).length() / 1024) + "KB)");
                retroLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");
            }
            if (studentePending == null) {
                statoLabel.setText("Errore: dati studente non disponibili");
                return;
            }
            statoLabel.setText("Elaborazione in corso...");
            statoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #6366f1;");
            String testoFronte = verificaOcrCtrl.estraiTesto(fronteData);
            String testoRetro = retroData != null ? verificaOcrCtrl.estraiTesto(retroData) : "";
            String testoCompleto = (testoFronte + " " + testoRetro).toUpperCase();
            String crudo = testoCompleto.replaceAll("[^A-Z0-9\\s]", "").replaceAll("\\s+", " ");
            String cf = studentePending.getCf().toUpperCase().replaceAll("[^A-Z0-9]", "");
            String nome = studentePending.getNome().toUpperCase().replaceAll("[^A-Z\\s]", "").trim();
            String cognome = studentePending.getCognome().toUpperCase().replaceAll("[^A-Z\\s]", "").trim();
            boolean cfOK = crudo.contains(cf);
            if (!cfOK && cf.length() == 16) {
                for (int i = 0; i < cf.length(); i++) {
                    char c = cf.charAt(i);
                    if (!Character.isLetterOrDigit(c)) break;
                }
                for (String parola : crudo.split("\\s+")) {
                    String p = parola.replaceAll("[^A-Z0-9]", "");
                    if (p.equals(cf)) { cfOK = true; break; }
                    if (p.length() >= 14 && p.contains(cf.substring(0, 10))) { cfOK = true; break; }
                }
            }
            boolean nomeOK = crudo.contains(nome);
            boolean cognomeOK = crudo.contains(cognome);
            if (!nomeOK && nome.length() > 2) {
                for (String parola : crudo.split("\\s+")) {
                    String p = parola.replaceAll("[^A-Z]", "");
                    if (p.equals(nome)) { nomeOK = true; break; }
                    if (p.length() >= 3 && nome.startsWith(p)) { nomeOK = true; break; }
                }
            }
            if (!cognomeOK && cognome.length() > 2) {
                for (String parola : crudo.split("\\s+")) {
                    String p = parola.replaceAll("[^A-Z]", "");
                    if (p.equals(cognome)) { cognomeOK = true; break; }
                    if (p.length() >= 3 && cognome.startsWith(p)) { cognomeOK = true; break; }
                }
            }
            if (cfOK && nomeOK && cognomeOK) {
                statoLabel.setText("");
                AlertUtils.mostraMessaggio("Verifica OCR", "Verifica riuscita");
                SceneManager.switchTo("FormPassword");
            } else {
                StringBuilder dettagli = new StringBuilder("Testo OCR:\n" + testoFronte);
                if (testoRetro.length() > 0) dettagli.append("\n--- retro ---\n").append(testoRetro);
                dettagli.append("\n\nRisultati:\n");
                dettagli.append("CF \"" + cf + "\" " + (cfOK ? "OK" : "NON TROVATO") + "\n");
                dettagli.append("Nome \"" + nome + "\" " + (nomeOK ? "OK" : "NON TROVATO") + "\n");
                dettagli.append("Cognome \"" + cognome + "\" " + (cognomeOK ? "OK" : "NON TROVATO"));
                statoLabel.setText("Verifica fallita: i dati non corrispondono (" + testoCompleto.length() + " caratteri letti)");
                statoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #ef4444;");
                AlertUtils.mostraErrore("Verifica OCR", dettagli.toString());
            }
        } catch (Exception e) {
            statoLabel.setText("Errore OCR: " + e.getMessage());
            AlertUtils.mostraErrore("Errore OCR", e.getMessage());
        }
    }
}
