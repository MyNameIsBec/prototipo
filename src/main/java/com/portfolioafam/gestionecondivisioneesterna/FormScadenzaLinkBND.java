package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;
public class FormScadenzaLinkBND {
    @FXML private DatePicker scadenzaPicker;
    private GeneraLinkPortfolioCTRL generaLinkPortfolioCtrl;
    public FormScadenzaLinkBND() {}
    public void setGeneraLinkPortfolioCtrl(GeneraLinkPortfolioCTRL c) { this.generaLinkPortfolioCtrl = c; }
    @FXML private void initialize() {
        scadenzaPicker.setValue(LocalDate.now().plusDays(30));
    }
    @FXML private void handleIndietro() { SceneManager.switchTo("SchermataCondivisioni"); }
    @FXML private void handleGenera() {
        LocalDate data = scadenzaPicker.getValue();
        if (data == null) { AlertUtils.mostraErrore("Errore", "Seleziona una data di scadenza"); return; }
        if (data.isBefore(LocalDate.now())) { AlertUtils.mostraErrore("Errore", "La data deve essere futura"); return; }
        try {
            LinkEntity link = generaLinkPortfolioCtrl.genera(Date.valueOf(data));
            String url = "http://localhost:8080/link/" + link.getToken();
            AlertUtils.mostraMessaggio("Link generato", "Link: " + url);
            SceneManager.switchToFresh("SchermataCondivisioni");
        } catch (Exception e) {
            AlertUtils.mostraErrore("Errore", "Impossibile generare il link: " + e.getMessage());
        }
    }
}
