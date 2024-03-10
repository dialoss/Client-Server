package Client;

import Server.Response;

import java.util.Arrays;

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
        try {
            this.manager.execute(tokens[0], Arrays.copyOfRange(tokens, 1, tokens.length));
        } catch (RuntimeException e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }
}
