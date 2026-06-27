package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.List;

public class RicercaProfiloBND {
    @FXML private TextField ricercaField;
    @FXML private ComboBox<String> corsoCombo;
    @FXML private ComboBox<String> annoCombo;
    @FXML private CheckBox laureatoCheck;
    @FXML private VBox filtriBox;
    @FXML private VBox risultatiBox;
    @FXML private Button filtraButton;
    private RicercaProfiloCTRL ricercaProfiloCtrl;
    private static String ultimaQuery;
    private boolean filtriVisibili;

    public RicercaProfiloBND() {}

    public void setRicercaProfiloCtrl(RicercaProfiloCTRL c) { this.ricercaProfiloCtrl = c; }

    public static void setUltimaQuery(String q) { ultimaQuery = q; }

    @FXML
    private void initialize() {
        ricercaField.requestFocus();
        corsoCombo.getItems().addAll("Pianoforte", "Violino", "Canto", "Chitarra", "Flauto", "Percussioni", "Composizione");
        annoCombo.getItems().addAll("2023/24", "2024/25", "2025/26", "2026/27");
        if (ultimaQuery != null && !ultimaQuery.trim().isEmpty()) {
            ricercaField.setText(ultimaQuery);
            eseguiRicerca(ultimaQuery, null, null, false);
            ultimaQuery = null;
        }
    }

    @FXML
    private void handleCerca() {
        String q = ricercaField.getText();
        String corso = corsoCombo.getValue();
        String anno = annoCombo.getValue();
        boolean laureato = laureatoCheck.isSelected();
        eseguiRicerca(q, corso, anno, laureato);
    }

    @FXML
    private void handleToggleFiltri() {
        filtriVisibili = !filtriVisibili;
        filtriBox.setManaged(filtriVisibili);
        filtriBox.setVisible(filtriVisibili);
        filtraButton.setText(filtriVisibili ? "Chiudi filtri" : "Filtra");
    }

    @FXML
    private void handleApplicaFiltri() {
        String q = ricercaField.getText();
        String corso = corsoCombo.getValue();
        String anno = annoCombo.getValue();
        boolean laureato = laureatoCheck.isSelected();
        eseguiRicerca(q, corso, anno, laureato);
    }

    private void eseguiRicerca(String query, String corso, String anno, boolean laureato) {
        if (risultatiBox == null) return;
        risultatiBox.getChildren().clear();
        try {
            if (ricercaProfiloCtrl == null) return;
            List<StudenteEntity> risultati = ricercaProfiloCtrl.cercaConFiltri(query, corso, anno, laureato);
            if (risultati.isEmpty()) {
                Label empty = new Label("Nessun risultato trovato");
                empty.setStyle("-fx-padding: 20; -fx-text-fill: #888; -fx-font-size: 14px;");
                risultatiBox.getChildren().add(empty);
                return;
            }
            for (StudenteEntity s : risultati) {
                VBox card = new VBox(5);
                card.setStyle("-fx-background-color: white; -fx-padding: 12; -fx-background-radius: 6; -fx-border-color: #e0e0e0; -fx-border-radius: 6; -fx-cursor: hand;");
                Label nomeLabel = new Label(s.getNome() + " " + s.getCognome());
                nomeLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
                Label accademiciLabel = new Label(s.getDatiAccademici() != null ? s.getDatiAccademici() : "");
                accademiciLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");
                card.getChildren().addAll(nomeLabel, accademiciLabel);
                card.setOnMouseClicked(e -> {
                    SchermataProfiloStudenteBND.preloadData(
                        s.getCf(), s.getNome(), s.getCognome(), s.getDatiAccademici());
                    SceneManager.switchToFresh("SchermataProfiloStudente");
                });
                risultatiBox.getChildren().add(card);
            }
        } catch (SQLException e) {
            Label err = new Label("Database non disponibile");
            err.setStyle("-fx-padding: 20; -fx-text-fill: #e74c3c;");
            risultatiBox.getChildren().add(err);
        }
    }

    public void reset() {
        ricercaField.clear();
        if (filtriVisibili) handleToggleFiltri();
        corsoCombo.setValue(null);
        corsoCombo.getSelectionModel().clearSelection();
        annoCombo.setValue(null);
        annoCombo.getSelectionModel().clearSelection();
        laureatoCheck.setSelected(false);
        filtriVisibili = false;
        filtriBox.setManaged(false);
        filtriBox.setVisible(false);
        filtraButton.setText("Filtra");
        if (risultatiBox != null) risultatiBox.getChildren().clear();
        ultimaQuery = null;
    }

    @FXML
    private void handleAzzeraFiltri() {
        corsoCombo.setValue(null);
        corsoCombo.getSelectionModel().clearSelection();
        annoCombo.setValue(null);
        annoCombo.getSelectionModel().clearSelection();
        laureatoCheck.setSelected(false);
        eseguiRicerca(ricercaField.getText(), null, null, false);
    }

    @FXML
    private void handleIndietro() {
        reset();
        SceneManager.switchTo("HomePage");
    }
}
