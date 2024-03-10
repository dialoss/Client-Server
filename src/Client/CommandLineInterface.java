package Client;

import Server.Response;

public class CommandLineInterface extends IUserInterface {
    Shell shell;

    CommandLineInterface() {
        this.manager = new ClientCommandManager(this::processOutput);
        this.shell = new Shell();
    }

    private void processOutput(Object data) {
        this.shell.print((String) data);
    }

    private void processInput(String[] tokens) {
        String commandName = tokens[0];
        try {
            this.manager.execute(commandName);
        } catch (RuntimeException e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }
}
