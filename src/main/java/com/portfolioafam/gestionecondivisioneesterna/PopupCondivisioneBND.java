package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
public class PopupCondivisioneBND {
    @FXML private TextField linkField;
    public PopupCondivisioneBND() {}
    public void setLink(String link) { linkField.setText(link); }
    @FXML private void handleCopiaLink() {
        ClipboardContent cc = new ClipboardContent();
        cc.putString(linkField.getText());
        Clipboard.getSystemClipboard().setContent(cc);
        AlertUtils.mostraMessaggio("Link", "Link copiato con successo");
    }
    @FXML private void handleInviaEmail() {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Invia email"); d.setHeaderText("Inserisci l'email del destinatario");
        d.showAndWait().ifPresent(email ->
            AlertUtils.mostraMessaggio("Email", "Email inviata con successo")
        );
    }
}
