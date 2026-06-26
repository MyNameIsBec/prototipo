package com.portfolioafam.gestionesegnalazioni;
import com.portfolioafam.util.SceneManager;
import com.portfolioafam.util.SessionManager;
import javafx.fxml.FXML;
public class AdminDashboardBND {
    public AdminDashboardBND() {}
    @FXML private void handleVaiASegnalazioni() { SceneManager.switchTo("ListaSegnalazioni"); }
    @FXML private void handleLogout() { SessionManager.getInstance().terminaSessione(); SceneManager.switchTo("HomePage"); }
}
