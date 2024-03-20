package Client.Shell;

import Client.UserInterface;
import Client.UserManager;
import Common.Commands.Command;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Pair;

import java.util.Map;

public class CommandLineInterface extends UserInterface {
    private final IOdevice shell;
    private final CommandParser parser;

    public CommandLineInterface(IForm shellForm) {
        this.shell = shellForm.device;
        this.parser = new CommandParser(shellForm);
    }

    private void processOutput(Response response) {
        UserManager.setClient(response.getClient());
        if (response.code == Status.OK) this.shell.println(response.getMessage());
        else this.shell.error(response.getMessage());
    }

    private void processInput(String[] tokens) {
        try {
            Pair<Command, Map<String, Object>> command = this.parser.parse(tokens);
            Request r = new Request(command.a, command.b);
            r.setUserClient(UserManager.getClient());
            Response response = this.requestCallback.call(r);
            this.processOutput(response);
        } catch (Exception e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }
}