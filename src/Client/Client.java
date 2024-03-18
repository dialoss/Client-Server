package Client;

import Client.APIs.ClientAPI;
import Client.APIs.HttpAPI;
import Client.GUI.GUIManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    UserInterface userInterface;
    ClientAPI api;

    @Override
    public void start(Stage stage) throws Exception {
        this.userInterface = new GUIManager(stage);
        this.api = new HttpAPI();

        this.userInterface.setRequestCallback(this.api::request);
        this.userInterface.start();
        UserManager.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}