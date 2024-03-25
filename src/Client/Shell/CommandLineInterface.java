package Client.Shell;

import Client.GUI.UserInfo;
import Client.UserInterface;
import Client.UserManager;
import Common.Commands.Command;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.UserClient;
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
        if (response.code == Status.OK) this.shell.println(response.getMessage());
        else this.shell.error(response.getMessage());
    }

    private void processInput(String[] tokens) {
        try {
            Pair<Command, Map<String, Object>> command = this.parser.parse(tokens);
            Request r = new Request(command.a, command.b);
            Map<String, Object> args = command.b;
            if (command.a.getName().equals("login") || command.a.getName().equals("register"))
                UserManager.setClient(new UserClient(new UserInfo(
                                "Alex",
                                (String) args.get("login"),
                                (String) args.get("password"))).withId(0));

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