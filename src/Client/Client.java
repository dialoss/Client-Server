package Client;

public class Client {
    IUserInterface userInterface;

    public Client() {
        this.userInterface = new CommandLineInterface();
    }

    public void run() {
        this.userInterface.start();
    }
}
