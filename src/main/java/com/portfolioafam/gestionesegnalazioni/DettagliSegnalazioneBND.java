package com.portfolioafam.gestionesegnalazioni;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class DettagliSegnalazioneBND {
    @FXML private Label nomeContenutoLabel, dettagliContenutoLabel, anteprimaLabel;
    private EliminaContenutiCTRL eliminaContenutiCtrl;
    private Long idContenuto;
    public DettagliSegnalazioneBND() {}
    public void setEliminaContenutiCtrl(EliminaContenutiCTRL c) { this.eliminaContenutiCtrl = c; }
    public void setContenuto(Long id, String nome, String dettagli) { this.idContenuto = id; nomeContenutoLabel.setText(nome); dettagliContenutoLabel.setText(dettagli); }
    @FXML private void handleElimina() {
        boolean ok = AlertUtils.mostraConferma("Elimina", "Sei sicuro di voler eliminare questo contenuto?");
        if (ok) { try { eliminaContenutiCtrl.elimina(idContenuto); AlertUtils.mostraMessaggio("Eliminato", "Contenuto eliminato"); SceneManager.switchTo("ListaSegnalazioni"); } catch (Exception e) { AlertUtils.mostraErrore("Errore", e.getMessage()); } }
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("ListaSegnalazioni"); }
}
