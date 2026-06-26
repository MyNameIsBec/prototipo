package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;

public class RisultatiRicercaBND {
    @FXML private VBox risultatiBox;
    private RicercaProfiloCTRL ricercaProfiloCtrl;
    private static String ultimaQuery;

    public RisultatiRicercaBND() {}
    public void setRicercaProfiloCtrl(RicercaProfiloCTRL c) { this.ricercaProfiloCtrl = c; }
    public static void setUltimaQuery(String q) { ultimaQuery = q; }

    @FXML
    private void initialize() {
        if (ultimaQuery != null && !ultimaQuery.trim().isEmpty()) {
            eseguiRicerca(ultimaQuery);
            ultimaQuery = null;
        }
    }

    public void eseguiRicerca(String query) {
        if (risultatiBox == null) return;
        risultatiBox.getChildren().clear();
        try {
            if (ricercaProfiloCtrl == null) return;
            List<StudenteEntity> risultati = ricercaProfiloCtrl.cerca(query);
            if (risultati.isEmpty()) {
                risultatiBox.getChildren().add(new Label("Nessun risultato trovato"));
                return;
            }
            for (StudenteEntity s : risultati) {
                VBox card = new VBox(5);
                card.setStyle("-fx-background-color: white; -fx-padding: 12; -fx-background-radius: 6; -fx-border-color: #e0e0e0; -fx-border-radius: 6; -fx-cursor: hand;");
                card.getChildren().add(new Label(s.getNome() + " " + s.getCognome()));
                card.getChildren().add(new Label(s.getDatiAccademici() != null ? s.getDatiAccademici() : ""));
                card.setOnMouseClicked(e -> {
                    SchermataProfiloStudenteBND.preloadData(
                        s.getCf(), s.getNome(), s.getCognome(), s.getDatiAccademici());
                    SceneManager.switchTo("SchermataProfiloStudente");
                });
                risultatiBox.getChildren().add(card);
            }
        } catch (SQLException e) {
            risultatiBox.getChildren().add(new Label("Database non disponibile"));
        }
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("HomePage"); }
}
