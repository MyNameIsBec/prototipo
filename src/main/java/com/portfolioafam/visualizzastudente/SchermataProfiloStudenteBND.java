package com.portfolioafam.visualizzastudente;

import com.portfolioafam.model.*;
import com.portfolioafam.repository.*;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.SQLException;

public class SchermataProfiloStudenteBND {
    @FXML private Label nomeStudenteLabel, datiAccademiciLabel;
    @FXML private FlowPane cartellePane, contenutiPane;
    private StudenteRepository studenteRepository;
    private CartellaRepository cartellaRepository;
    private ContenutoRepository contenutoRepository;

    private static String cfToLoad;
    private static String nomeToLoad;
    private static String cognomeToLoad;
    private static String datiAccToLoad;

    public SchermataProfiloStudenteBND() {}

    public static void preloadData(String cf, String nome, String cognome, String datiAcc) {
        cfToLoad = cf; nomeToLoad = nome; cognomeToLoad = cognome; datiAccToLoad = datiAcc;
    }

    public void setRepositories(StudenteRepository sr, CartellaRepository cr, ContenutoRepository cor) {
        this.studenteRepository = sr; this.cartellaRepository = cr; this.contenutoRepository = cor;
    }

    @FXML
    private void initialize() {
        if (nomeToLoad != null) {
            caricaProfiloCompleto(cfToLoad, nomeToLoad, cognomeToLoad, datiAccToLoad);
            cfToLoad = null; nomeToLoad = null; cognomeToLoad = null; datiAccToLoad = null;
        }
    }

    public void caricaProfiloCompleto(String cf, String nome, String cognome, String datiAcc) {
        if (nomeStudenteLabel == null) return;
        nomeStudenteLabel.setText(nome + " " + cognome);
        datiAccademiciLabel.setText(datiAcc != null ? datiAcc : "");
        cartellePane.getChildren().clear();
        contenutiPane.getChildren().clear();
        try {
            if (cartellaRepository != null) {
                for (CartellaEntity c : cartellaRepository.findByCf(cf)) {
                    VBox card = new VBox(5);
                    card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 6; -fx-min-width: 180;");
                    card.getChildren().add(new Label(c.getNomeCartella()));
                    Button apri = new Button("Apri");
                    apri.setOnAction(e -> {
                        AlertUtils.mostraMessaggio("Cartella", "Contenuti di: " + c.getNomeCartella());
                    });
                    card.getChildren().add(apri);
                    cartellePane.getChildren().add(card);
                }
            }
            if (contenutoRepository != null) {
                for (ContenutoEntity c : contenutoRepository.findByCf(cf)) {
                    VBox card = new VBox(5);
                    card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 6; -fx-min-width: 180;");
                    card.getChildren().add(new Label(c.getNome()));

                    MenuButton menu = new MenuButton("\u22EE");
                    menu.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");
                    MenuItem scarica = new MenuItem("Scarica");
                    scarica.setOnAction(e -> AlertUtils.mostraMessaggio("Scarica", "Contenuto scaricato"));
                    MenuItem segnala = new MenuItem("Segnala");
                    segnala.setOnAction(e -> AlertUtils.mostraMessaggio("Segnala", "Segnalazione inviata con successo"));
                    menu.getItems().addAll(scarica, segnala);
                    card.getChildren().add(menu);

                    contenutiPane.getChildren().add(card);
                }
            }
        } catch (SQLException e) {}
    }

    @FXML private void handleIndietro() { SceneManager.switchTo("HomePage"); }
}
