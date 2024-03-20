package Client;

import Client.APIs.ClientAPI;
import Client.APIs.HttpAPI;
import Client.GUI.GUIManager;

public class Client {
    UserInterface userInterface;
    ClientAPI api;

    public void start() throws Exception {
        UserManager.init();
        this.userInterface = new GUIManager();
        this.api = new HttpAPI();

        this.userInterface.setRequestCallback(this.api::request);
        this.userInterface.start();
    }
}