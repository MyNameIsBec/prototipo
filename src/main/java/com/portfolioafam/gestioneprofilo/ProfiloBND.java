package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.autenticazione.SchermataVerifica2FABND;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;

public class ProfiloBND {
    @FXML private ImageView immagineProfilo;
    @FXML private Label nomeCognomeLabel, cfLabel, emailLabel, telefonoLabel, cfDettaglioLabel, nascitaLabel, datiAccademiciLabel;
    private VisualizzaProfiloCTRL visualizzaProfiloCtrl;
    private ModificaImmagineCTRL modificaImmagineCtrl;
    private SchermataVerifica2FABND verifica2faBnd;

    public ProfiloBND() {}

    public void setVisualizzaProfiloCtrl(VisualizzaProfiloCTRL c) { this.visualizzaProfiloCtrl = c; }
    public void setModificaImmagineCtrl(ModificaImmagineCTRL c) { this.modificaImmagineCtrl = c; }
    public void setVerifica2faBnd(SchermataVerifica2FABND b) { this.verifica2faBnd = b; }

    @FXML
    private void initialize() {
        caricaProfilo();
    }

    private void caricaProfilo() {
        if (!SessionManager.getInstance().isAutenticato()) {
            System.err.println("[LOGIN DEBUG] ProfiloBND.caricaProfilo: nessuna sessione attiva");
            return;
        }

        StudenteEntity s = null;
        try {
            if (visualizzaProfiloCtrl != null) {
                s = visualizzaProfiloCtrl.getProfilo();
            }
        } catch (Exception e) {
            System.err.println("[LOGIN DEBUG] DB non disponibile per profilo: " + e.getMessage());
        }

        if (s == null) {
            s = SessionManager.getInstance().getStudente();
        }

        if (s == null) {
            System.err.println("[LOGIN DEBUG] ProfiloBND.caricaProfilo: studente null");
            return;
        }
        System.err.println("[LOGIN DEBUG] ProfiloBND.caricaProfilo: studente=" + s.getNome() + " " + s.getCognome());

        nomeCognomeLabel.setText(s.getNome() + " " + s.getCognome());
        cfLabel.setText("CF: " + s.getCf());
        cfDettaglioLabel.setText(s.getCf());
        emailLabel.setText(s.getEmail() != null ? s.getEmail() : "Non impostata");
        telefonoLabel.setText(s.getTelefono() != null ? s.getTelefono() : "Non impostato");
        datiAccademiciLabel.setText(s.getDatiAccademici() != null ? s.getDatiAccademici() : "Non specificati");
        nascitaLabel.setText("Non specificata");

        try {
            if (s.getImmagineProfilo() != null) {
                immagineProfilo.setImage(new Image(new ByteArrayInputStream(s.getImmagineProfilo())));
            } else {
                mostraPlaceholder(s.getNome(), s.getCognome());
            }
        } catch (Exception e) {
            mostraPlaceholder(s.getNome(), s.getCognome());
        }
    }

    private void mostraPlaceholder(String nome, String cognome) {
        Circle cerchio = new Circle(40, Color.web("#6366f1"));
        Text iniziali = new Text((nome != null && !nome.isEmpty() ? nome.charAt(0) + "" : "") +
                (cognome != null && !cognome.isEmpty() ? cognome.charAt(0) + "" : "?"));
        iniziali.setFill(Color.WHITE);
        iniziali.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        StackPane padre = (StackPane) immagineProfilo.getParent();
        padre.getChildren().add(0, new StackPane(cerchio, iniziali));
        immagineProfilo.setVisible(false);
    }

    @FXML
    private void handleModificaImmagine() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.jpeg", "*.png"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            try {
                byte[] data = Files.readAllBytes(f.toPath());
                immagineProfilo.setImage(new Image(new ByteArrayInputStream(data)));
                if (modificaImmagineCtrl != null) {
                    int dot = f.getName().lastIndexOf('.');
                    String ext = dot > 0 ? f.getName().substring(dot + 1) : "jpg";
                    modificaImmagineCtrl.modificaImmagine(data, ext);
                    AlertUtils.mostraMessaggio("Immagine", "Immagine aggiornata");
                } else {
                    AlertUtils.mostraMessaggio("Immagine", "Immagine visualizzata (DB non disponibile)");
                }
            } catch (Exception e) {
                AlertUtils.mostraErrore("Errore", e.getMessage());
            }
        }
    }

    @FXML private void handleVaiAContenuti() { SceneManager.switchTo("IMieiContenuti"); }
    @FXML private void handleVaiACondivisioni() { SceneManager.switchTo("SchermataCondivisioni"); }
    @FXML private void handleModificaEmail() { SceneManager.switchTo("ModificaEmail"); }
    @FXML private void handleModificaPassword() { SceneManager.switchTo("ModificaPassword"); }
    @FXML private void handleModificaTelefono() { SceneManager.switchTo("ModificaTelefono"); }
    @FXML private void handleModificaDatiAccademici() { SceneManager.switchTo("ModificaDatiAccademici"); }
    @FXML private void handleModifica2FA() {
        if (verifica2faBnd != null) {
            verifica2faBnd.setPaginaPrecedente("SchermataProfilo");
        }
        SceneManager.switchTo("SchermataVerifica2FA");
    }
    @FXML private void handleModificaVisibilita() { SceneManager.switchTo("SchermataVisibilitaProfilo"); }
    @FXML private void handleEliminaAccount() { SceneManager.switchTo("Informativa"); }
    @FXML private void handleLogout() {
        if (AlertUtils.mostraConferma("Logout", "Sei sicuro di voler uscire?")) {
            SessionManager.getInstance().terminaSessione();
            SceneManager.switchTo("HomePage");
        }
    }
}
