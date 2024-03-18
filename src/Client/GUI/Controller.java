package Client.GUI;

import Server.Models.MObject;
import Server.Models.Organization;
import Server.Storage.StorageConnector;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    public void click(javafx.event.ActionEvent actionEvent) {
        btn.setText("You've clicked!");
    }



    public void x(String text) {
        try {
            MObject[] t = StorageConnector.dbManager.getSession().getAll(Organization.class);
            this.name.setText(text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
