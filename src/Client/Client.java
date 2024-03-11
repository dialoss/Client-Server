package Client;

public class Client {
    UserInterface userInterface;
    ClientAPI api;

    public Client() {
        this.userInterface = new CommandLineInterface();
        this.api = new ClientAPI(this.userInterface.getInterface());
        this.userInterface.setRequestCallback(this.api::request);
    }

    public void run() {
        this.userInterface.start();
    }
}
