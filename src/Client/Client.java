package Client;

public class Client {
    UserInterface userInterface;

    public Client() {
        this.userInterface = new CommandLineInterface();
    }

    public void run() {
        this.userInterface.start();
    }
}
