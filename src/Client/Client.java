package Client;

import Client.APIs.ClientAPI;
import Client.APIs.TcpAPI;
import Client.GUI.GUIManager;

public class Client {
    UserInterface userInterface;
    ClientAPI api;

    public void start() {
        UserManager.init();
        this.userInterface = new GUIManager();
        this.api = new TcpAPI();
        this.api.connect();
        this.userInterface.setRequestCallback(this.api::request);
        this.userInterface.start();
    }
}