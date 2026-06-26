package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.List;
public class SchermataRicercaProfiloBND {
    @FXML private TextField ricercaField;
    @FXML private ListView<String> risultatiList;
    private RicercaProfiloCTRL ricercaProfiloCtrl;
    public SchermataRicercaProfiloBND() {}
    public void setRicercaProfiloCtrl(RicercaProfiloCTRL c) { this.ricercaProfiloCtrl = c; }
    @FXML private void handleCerca() {
        try {
            if (ricercaProfiloCtrl != null) {
                List<StudenteEntity> risultati = ricercaProfiloCtrl.cerca(ricercaField.getText());
                risultatiList.getItems().clear();
                for (StudenteEntity s : risultati) risultatiList.getItems().add(s.getNome() + " " + s.getCognome());
            }
        } catch (SQLException e) { AlertUtils.mostraErrore("Errore", "Ricerca fallita"); }
    }
}
