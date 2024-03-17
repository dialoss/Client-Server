package Client;

import Client.APIs.ClientAPI;
import Client.APIs.EventBusAPI;
import Client.GUI.GUIManager;
import atlantafx.base.theme.NordLight;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {
    UserInterface userInterface;
    ClientAPI api;

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        this.userInterface = new GUIManager(stage);
        this.api = new EventBusAPI();

        this.userInterface.setRequestCallback(this.api::request);
        this.userInterface.start();
        this.api.setResponseCallback(this.userInterface.getInterface());
        UserManager.init();

    }

    public static void main(String[] args) {
        launch(args);
    }
}