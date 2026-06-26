package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.*;
import com.portfolioafam.repository.*;
import com.portfolioafam.gestionecondivisioneesterna.*;
import com.portfolioafam.gestioneprivacy.*;
import com.portfolioafam.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

public class IMieiContenutiBND {
    @FXML private FlowPane cartellePane, contenutiPane;
    @FXML private TextField ricercaField;
    private VisualizzaContenutiCTRL visualizzaContenutiCtrl;
    private EliminaContenutoCTRL eliminaContenutoCtrl;
    private CaricaContenutiCTRL caricaContenutiCtrl;
    private RicercaDocumentoCTRL ricercaDocumentoCtrl;
    private CreaCartellaCTRL creaCartellaCtrl;
    private EliminaCartellaCTRL eliminaCartellaCtrl;
    private ScaricaContenutoCTRL scaricaContenutoCtrl;
    private ScaricaCartellaCTRL scaricaCartellaCtrl;
    private SpostaContenutoCTRL spostaContenutoCtrl;
    private DuplicaContenutoCTRL duplicaContenutoCtrl;
    private ModificaContenutoCTRL modificaContenutoCtrl;
    private ModificaCartellaCTRL modificaCartellaCtrl;
    private ModificaPrivacyContenutoCTRL modificaPrivacyContenutoCtrl;
    private ModificaPrivacyCartellaCTRL modificaPrivacyCartellaCtrl;
    private GeneraLinkCartellaCTRL generaLinkCartellaCtrl;
    private GeneraLinkContenutoCTRL generaLinkContenutoCtrl;
    private CartellaRepository cartellaRepository;
    private ContenutoRepository contenutoRepository;

    public IMieiContenutiBND() {}

    public void setDependencies(VisualizzaContenutiCTRL vc, EliminaContenutoCTRL ec, CaricaContenutiCTRL cc,
                                RicercaDocumentoCTRL rc, CreaCartellaCTRL crc, CartellaRepository cr) {
        this.visualizzaContenutiCtrl = vc; this.eliminaContenutoCtrl = ec; this.caricaContenutiCtrl = cc;
        this.ricercaDocumentoCtrl = rc; this.creaCartellaCtrl = crc; this.cartellaRepository = cr;
    }
    public void setExtraDependencies(EliminaCartellaCTRL ec, ScaricaContenutoCTRL sc, ScaricaCartellaCTRL sc2,
                                      SpostaContenutoCTRL sp, DuplicaContenutoCTRL du, ModificaContenutoCTRL mc,
                                      ModificaCartellaCTRL mc2, ModificaPrivacyContenutoCTRL mp,
                                      ModificaPrivacyCartellaCTRL mp2, GeneraLinkCartellaCTRL gl,
                                      GeneraLinkContenutoCTRL gl2, ContenutoRepository cr) {
        this.eliminaCartellaCtrl = ec; this.scaricaContenutoCtrl = sc; this.scaricaCartellaCtrl = sc2;
        this.spostaContenutoCtrl = sp; this.duplicaContenutoCtrl = du; this.modificaContenutoCtrl = mc;
        this.modificaCartellaCtrl = mc2; this.modificaPrivacyContenutoCtrl = mp; this.modificaPrivacyCartellaCtrl = mp2;
        this.generaLinkCartellaCtrl = gl; this.generaLinkContenutoCtrl = gl2; this.contenutoRepository = cr;
    }

    @FXML private void initialize() { if (SessionManager.getInstance().isAutenticato()) caricaContenuti(); }

    private void caricaContenuti() {
        try {
            cartellePane.getChildren().clear();
            contenutiPane.getChildren().clear();
            if (visualizzaContenutiCtrl != null) {
                for (ContenutoEntity c : visualizzaContenutiCtrl.getContenuti())
                    contenutiPane.getChildren().add(creaCardContenuto(c));
            }
            String cf = SessionManager.getInstance().getCf();
            if (cf != null && cartellaRepository != null) {
                for (CartellaEntity c : cartellaRepository.findByCf(cf))
                    cartellePane.getChildren().add(creaCardCartella(c));
            }
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Impossibile caricare i contenuti"); }
    }

    private VBox creaCardCartella(CartellaEntity c) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-min-width: 180;");
        card.getChildren().add(new Label("\uD83D\uDCC1"));
        card.getChildren().add(new Label(c.getNomeCartella()));

        MenuButton menu = new MenuButton("\u22EE");
        menu.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");
        MenuItem modifica = new MenuItem("Modifica"); modifica.setOnAction(e -> modificaCartella(c));
        MenuItem elimina = new MenuItem("Elimina"); elimina.setOnAction(e -> eliminaCartella(c));
        MenuItem scarica = new MenuItem("Scarica"); scarica.setOnAction(e -> scaricaCartella(c));
        MenuItem privacy = new MenuItem("Modifica privacy"); privacy.setOnAction(e -> modificaPrivacyCartella(c));
        MenuItem genLink = new MenuItem("Genera link"); genLink.setOnAction(e -> generaLinkCartella(c));
        menu.getItems().addAll(modifica, elimina, scarica, privacy, genLink);
        card.getChildren().add(menu);
        return card;
    }

    private VBox creaCardContenuto(ContenutoEntity c) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-radius: 8; -fx-min-width: 180;");
        String icona = c.getTipo() != null && c.getTipo().contains("PDF") ? "\uD83D\uDCC4" :
                        c.getTipo() != null && c.getTipo().contains("IMMAGINE") ? "\uD83D\uDDBC" :
                        c.getTipo() != null && c.getTipo().contains("AUDIO") ? "\uD83C\uDFB5" : "\uD83D\uDCC1";
        card.getChildren().add(new Label(icona));
        card.getChildren().add(new Label(c.getNome()));

        MenuButton menu = new MenuButton("\u22EE");
        menu.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");
        MenuItem modifica = new MenuItem("Modifica"); modifica.setOnAction(e -> modificaContenuto(c));
        MenuItem elimina = new MenuItem("Elimina"); elimina.setOnAction(e -> eliminaContenuto(c));
        MenuItem scarica = new MenuItem("Scarica"); scarica.setOnAction(e -> scaricaContenuto(c));
        MenuItem sposta = new MenuItem("Sposta"); sposta.setOnAction(e -> spostaContenuto(c));
        MenuItem duplica = new MenuItem("Duplica"); duplica.setOnAction(e -> duplicaContenuto(c));
        MenuItem privacy = new MenuItem("Modifica privacy"); privacy.setOnAction(e -> modificaPrivacyContenuto(c));
        MenuItem genLink = new MenuItem("Genera link"); genLink.setOnAction(e -> generaLinkContenuto(c));
        menu.getItems().addAll(modifica, elimina, scarica, sposta, duplica, privacy, genLink);
        card.getChildren().add(menu);
        return card;
    }

    private void modificaContenuto(ContenutoEntity c) {
        TextInputDialog d = new TextInputDialog(c.getNome());
        d.setTitle("Modifica contenuto"); d.setHeaderText("Modifica " + c.getNome());
        d.setContentText("Nome:");
        d.showAndWait().ifPresent(n -> { try { modificaContenutoCtrl.modifica(c.getIdContenuto(), n, c.getTipo(), c.getPrivacy()); AlertUtils.mostraMessaggio("Contenuto", "Contenuto modificato con successo"); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } });
    }
    private void eliminaContenuto(ContenutoEntity c) {
        if (AlertUtils.mostraConferma("Elimina", "Eliminare " + c.getNome() + "?")) { try { eliminaContenutoCtrl.elimina(c.getIdContenuto()); AlertUtils.mostraMessaggio("Contenuto", "Contenuto eliminato"); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } }
    }
    private void scaricaContenuto(ContenutoEntity c) {
        try { AlertUtils.mostraMessaggio("Scarica", "Contenuto scaricato"); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    private void spostaContenuto(ContenutoEntity c) {
        try {
            List<CartellaEntity> cartelle = cartellaRepository.findByCf(SessionManager.getInstance().getCf());
            ChoiceDialog<CartellaEntity> d = new ChoiceDialog<>(null, cartelle);
            d.setTitle("Sposta"); d.setHeaderText("Scegli cartella destinazione");
            d.showAndWait().ifPresent(dest -> { try { spostaContenutoCtrl.sposta(c.getIdContenuto(), dest.getIdCartella()); AlertUtils.mostraMessaggio("Contenuto", "Contenuto spostato"); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } });
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    private void duplicaContenuto(ContenutoEntity c) {
        try {
            List<CartellaEntity> cartelle = cartellaRepository.findByCf(SessionManager.getInstance().getCf());
            ChoiceDialog<CartellaEntity> d = new ChoiceDialog<>(null, cartelle);
            d.setTitle("Duplica"); d.setHeaderText("Scegli cartella destinazione");
            d.showAndWait().ifPresent(dest -> { try { duplicaContenutoCtrl.duplica(c.getIdContenuto(), dest != null ? dest.getIdCartella() : null); AlertUtils.mostraMessaggio("Contenuto", "Contenuto duplicato"); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } });
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    private void modificaPrivacyContenuto(ContenutoEntity c) {
        ChoiceDialog<String> d = new ChoiceDialog<>("PRIVATO", "PUBBLICO", "PRIVATO", "PUBBLICO_SCARICABILE");
        d.setTitle("Privacy contenuto"); d.setHeaderText("Scegli livello privacy");
        d.showAndWait().ifPresent(p -> { try { modificaPrivacyContenutoCtrl.modificaPrivacy(c.getIdContenuto(), p); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } });
    }
    private void generaLinkContenuto(ContenutoEntity c) {
        try {
            java.sql.Date scad = new java.sql.Date(System.currentTimeMillis() + 7L * 86400000);
            generaLinkContenutoCtrl.genera(c.getIdContenuto(), scad);
            AlertUtils.mostraMessaggio("Link", "Link generato");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }

    private void modificaCartella(CartellaEntity c) {
        TextInputDialog d = new TextInputDialog(c.getNomeCartella());
        d.setTitle("Modifica cartella"); d.setHeaderText("Modifica " + c.getNomeCartella());
        d.setContentText("Nome:");
        d.showAndWait().ifPresent(n -> { try { modificaCartellaCtrl.modifica(c.getIdCartella(), n, c.getPrivacy()); AlertUtils.mostraMessaggio("Cartella", "Cartella modificata con successo"); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } });
    }
    private void eliminaCartella(CartellaEntity c) {
        if (AlertUtils.mostraConferma("Elimina", "Eliminare cartella " + c.getNomeCartella() + " e tutti i suoi contenuti?")) { try { eliminaCartellaCtrl.elimina(c.getIdCartella()); AlertUtils.mostraMessaggio("Cartella", "Cartella eliminata"); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } }
    }
    private void scaricaCartella(CartellaEntity c) {
        try { AlertUtils.mostraMessaggio("Scarica", "Cartella scaricata"); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }
    private void modificaPrivacyCartella(CartellaEntity c) {
        ChoiceDialog<String> d = new ChoiceDialog<>("PRIVATO", "PUBBLICO", "PRIVATO", "PUBBLICO_SCARICABILE");
        d.setTitle("Privacy cartella"); d.setHeaderText("Scegli livello privacy (ereditato dai contenuti)");
        d.showAndWait().ifPresent(p -> { try { modificaPrivacyCartellaCtrl.modificaPrivacy(c.getIdCartella(), p); caricaContenuti(); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } });
    }
    private void generaLinkCartella(CartellaEntity c) {
        try {
            java.sql.Date scad = new java.sql.Date(System.currentTimeMillis() + 7L * 86400000);
            generaLinkCartellaCtrl.genera(c.getIdCartella(), scad);
            AlertUtils.mostraMessaggio("Link", "Link generato");
        } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
    }

    @FXML private void handleFiltra() {
        ChoiceDialog<String> d = new ChoiceDialog<>("Tutti", "PDF", "AUDIO", "IMMAGINE", "DOCUMENTO", "VIDEO", "ALTRO");
        d.setTitle("Filtra"); d.setHeaderText("Seleziona tipo contenuto");
        d.showAndWait().ifPresent(filtro -> {
            contenutiPane.getChildren().clear();
            try {
                for (ContenutoEntity c : visualizzaContenutiCtrl.getContenuti())
                    if ("Tutti".equals(filtro) || c.getTipo().equals(filtro))
                        contenutiPane.getChildren().add(creaCardContenuto(c));
            } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Filtro non disponibile"); }
        });
    }

    @FXML private void handleCaricaContenuto() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleziona file");
        File f = fc.showOpenDialog(null);
        if (f != null) {
            try {
                byte[] data = Files.readAllBytes(f.toPath());
                String nome = f.getName();
                String tipo = rilevaTipo(f.getName());
                long dim = f.length();
                caricaContenutiCtrl.carica(data, nome, tipo, dim, "PRIVATO", null);
                AlertUtils.mostraMessaggio("Carica", "File caricato");
                caricaContenuti();
            } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); }
        }
    }

    @FXML private void handleNuovaCartella() {
        TextInputDialog d = new TextInputDialog("Nuova cartella");
        d.setTitle("Nuova cartella"); d.setHeaderText("Inserisci il nome della cartella");
        d.showAndWait().ifPresent(nome -> {
            try {
                creaCartellaCtrl.crea(nome, "PRIVATO");
                AlertUtils.mostraMessaggio("Cartella", "Cartella " + nome + " creata con successo");
                caricaContenuti();
            } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Impossibile creare la cartella"); }
        });
    }

    @FXML private void handleCerca() {
        try {
            contenutiPane.getChildren().clear();
            for (ContenutoEntity c : ricercaDocumentoCtrl.cerca(ricercaField.getText()))
                contenutiPane.getChildren().add(creaCardContenuto(c));
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Ricerca fallita"); }
    }

    @FXML private void handleVaiAProfilo() { SceneManager.switchTo("SchermataProfilo"); }
    @FXML private void handleVaiACondivisioni() { SceneManager.switchTo("SchermataCondivisioni"); }
    private String rilevaTipo(String nomeFile) {
        if (nomeFile == null) return "DOCUMENTO";
        String n = nomeFile.toLowerCase();
        if (n.endsWith(".pdf")) return "PDF";
        if (n.endsWith(".mp3") || n.endsWith(".wav") || n.endsWith(".flac")) return "AUDIO";
        if (n.endsWith(".mp4") || n.endsWith(".avi") || n.endsWith(".mov")) return "VIDEO";
        if (n.endsWith(".jpg") || n.endsWith(".jpeg") || n.endsWith(".png") || n.endsWith(".gif")) return "IMMAGINE";
        return "DOCUMENTO";
    }

    @FXML private void handleLogout() { if (AlertUtils.mostraConferma("Logout", "Sei sicuro di voler uscire?")) { SessionManager.getInstance().terminaSessione(); SceneManager.switchTo("HomePage"); } }
}
