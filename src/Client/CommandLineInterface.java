package Client;


public class CommandLineInterface implements IUserInterface {
    CommandManager commandManager;
    Shell shell;

    CommandLineInterface(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.shell = new Shell(commandManager);
    }

    public void start() {
//        this.shell.start();
    }
}
