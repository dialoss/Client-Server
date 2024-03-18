package Client.Shell;

import Client.UserInterface;
import Client.UserManager;
import Common.Commands.Command;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Pair;

public class CommandLineInterface extends UserInterface {
    private final IOdevice shell;
    private final CommandParser parser;

    public CommandLineInterface(IForm shellForm) {
        this.shell = shellForm.device;
        this.parser = new CommandParser(shellForm);
    }

    private void processOutput(Response response) {
        UserManager.setClient(response.getClient());
        if (response.code == Status.OK) this.shell.println((String) response.getBody());
        else this.shell.error((String) response.getBody());
    }

    private void processInput(String[] tokens) {
        try {
            Pair<Command, Object[]> command = this.parser.parse(tokens);
            UserManager.processAuth(command);
            Response response = this.requestCallback.call(new Request(command.a, command.b));
            this.processOutput(response);
        } catch (Exception e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }
}