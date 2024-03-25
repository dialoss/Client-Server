package Client;

import Client.APIs.ClientAPI;
import Client.APIs.TcpAPI;
import Client.Shell.CommandLineInterface;
import Client.Shell.Shell;
import Client.Shell.ShellForm;

public class Client {
    UserInterface userInterface;
    ClientAPI api;

    public void start() {
        UserManager.init();
        this.userInterface = new CommandLineInterface(new ShellForm(new Shell()));
        this.api = new TcpAPI();

        this.userInterface.setRequestCallback(this.api::request);
        this.userInterface.start();
    }
}