package com.portfolioafam.gestionesegnalazioni;
import com.portfolioafam.model.SegnalazioneEntity;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class ListaSegnalazioniBND {
    @FXML private TableView<SegnalazioneEntity> segnalazioniTable;
    private SegnalazioniCTRL segnalazioniCtrl;
    public ListaSegnalazioniBND() {}
    public void setSegnalazioniCtrl(SegnalazioniCTRL c) { this.segnalazioniCtrl = c; }
    @FXML private void initialize() { caricaSegnalazioni(); }
    private void caricaSegnalazioni() {
        try {
            if (segnalazioniCtrl != null) segnalazioniTable.setItems(FXCollections.observableArrayList(segnalazioniCtrl.getSegnalazioni()));
        } catch (Exception e) { }
    }
    @FXML private void handleDashboard() { SceneManager.switchTo("AdminDashboard"); }
    @FXML private void handleLogout() { SessionManager.getInstance().terminaSessione(); SceneManager.switchTo("HomePage"); }
}
