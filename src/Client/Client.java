package Client;

import Client.APIs.ClientAPI;
import Client.APIs.EventBusAPI;
import Client.GUI.GUIManager;
import Server.Server;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class Client extends Application {
    UserInterface userInterface;
    ClientAPI api;

    @Override
    public void start(Stage stage) throws Exception {
        URL css = Paths.get("C:\\Users\\dialoss\\IdeaProjects\\lab5\\src\\Client\\GUI\\style.css").toUri().toURL();

        Application.setUserAgentStylesheet(css.toExternalForm());

        this.userInterface = new GUIManager(stage);
        this.api = new EventBusAPI();

        this.userInterface.setRequestCallback(this.api::request);
        this.userInterface.start();
        this.api.setResponseCallback(this.userInterface.getInterface());
        UserManager.init();

    }

    public static void main(String[] args) {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

        launch(args);
    }
}