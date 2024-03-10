package Client;

public class Client {
    CommandManager commandManager;
    IUserInterface userInterface;

    public Client() {
        this.commandManager = new CommandManager();
        new CommandManagerBuilder().build(this.commandManager);
        this.userInterface = new CommandLineInterface(this.commandManager);
    }

    public void run() {
        this.userInterface.start();
    }
}
