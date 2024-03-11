package Client;

import Client.Shell.Shell;

public class CommandLineInterface extends IUserInterface {
    Shell shell;

    CommandLineInterface() {
        this.manager = new ClientCommandManager(this::processOutput);
        this.shell = new Shell();
    }

    private void processOutput(Object data) {
        this.shell.print((String) data);
    }

    private void processInput(String commandName) {
        try {
            this.manager.execute(commandName, this.shell);
        } catch (RuntimeException e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }
}
