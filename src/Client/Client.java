package Client;

import Client.Shell.Shell;
import Client.Shell.ShellForm;

public class Client {
    UserInterface userInterface;
    ClientAPI api;

    public Client() {
        this.userInterface = new CommandLineInterface(new ShellForm(new Shell()));
        this.api = new ClientAPI(this.userInterface.getInterface());
        this.userInterface.setRequestCallback(this.api::request);
    }

    public void run() {
        this.userInterface.start();
    }
}
