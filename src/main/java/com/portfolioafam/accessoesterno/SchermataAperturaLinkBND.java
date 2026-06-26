package com.portfolioafam.accessoesterno;
import com.portfolioafam.model.*;
import com.portfolioafam.repository.*;
import com.portfolioafam.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
public class SchermataAperturaLinkBND {
    @FXML private VBox sidebar;
    @FXML private ImageView anteprimaImmagine;
    @FXML private Label nomeFileLabel, metadatiLabel;
    private LinkRepository linkRepository;
    private ContenutoRepository contenutoRepository;
    private LinkEntity link;
    public SchermataAperturaLinkBND() {}
    public void setRepositories(LinkRepository lr, ContenutoRepository cr) { this.linkRepository = lr; this.contenutoRepository = cr; }
    public void caricaLink(String token) {
        try {
            link = linkRepository.findByToken(token).orElseThrow();
            if ("SCADUTO".equals(link.getStato()) || "REVOCATO".equals(link.getStato())) { AlertUtils.mostraErrore("Link", "Link scaduto"); return; }
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Link non valido"); }
    }
    @FXML private void handleValuta() {
        ChoiceDialog<String> d = new ChoiceDialog<>("5 stelle", "1", "2", "3", "4", "5");
        d.setTitle("Valuta"); d.setHeaderText("Lascia un riscontro");
        d.showAndWait().ifPresent(v -> AlertUtils.mostraMessaggio("Riscontro", "Valutazione inviata con successo"));
    }
    @FXML private void handleSegnala() {
        SceneManager.switchTo("InviaSegnalazione");
    }
}
