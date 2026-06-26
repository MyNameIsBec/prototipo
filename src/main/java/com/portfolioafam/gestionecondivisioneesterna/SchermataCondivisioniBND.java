package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.model.LinkEntity;
import com.portfolioafam.util.AlertUtils;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import java.sql.SQLException;
import java.util.List;
public class SchermataCondivisioniBND {
    @FXML private TableView<LinkEntity> linksTable;
    @FXML private FlowPane contenutiCondivisionePane;
    private CondivisioniCTRL condivisioniCtrl;
    private GeneraLinkPortfolioCTRL generaLinkPortfolioCtrl;
    private RevocaLinkCTRL revocaLinkCtrl;
    public SchermataCondivisioniBND() {}
    public void setDependencies(CondivisioniCTRL cc, GeneraLinkPortfolioCTRL gl, RevocaLinkCTRL rl) {
        this.condivisioniCtrl = cc; this.generaLinkPortfolioCtrl = gl; this.revocaLinkCtrl = rl;
    }
    @FXML private void initialize() { if (SessionManager.getInstance().isAutenticato()) caricaLinks(); }
    private void caricaLinks() {
        try {
            if (condivisioniCtrl != null) {
                List<LinkEntity> links = condivisioniCtrl.getLinks();
                linksTable.getItems().clear();
                linksTable.getItems().addAll(links);
            }
        } catch (SQLException e) { }
    }
    @FXML private void handleGeneraLink() { SceneManager.switchTo("FormScadenzaLink"); }
    @FXML private void handleVaiAProfilo() { SceneManager.switchTo("SchermataProfilo"); }
    @FXML private void handleVaiAContenuti() { SceneManager.switchTo("IMieiContenuti"); }
    @FXML private void handleLogout() { SessionManager.getInstance().terminaSessione(); SceneManager.switchTo("HomePage"); }
}
