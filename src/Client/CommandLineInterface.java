package Client;

import Client.Shell.IForm;
import Client.Shell.IOdevice;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.EventBus.Callback;
import Common.Pair;
import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

public class CommandLineInterface implements UserInterface {
    private final IOdevice shell;
    private Callback<Request> requestCallback;
    private final CommandParser parser;

    public CommandLineInterface(IForm shellForm) {
        this.shell = shellForm.device;
        this.parser = new CommandParser(shellForm);
    }

    private void processOutput(Response response) {
        UserManager.setClient(response.getClient());
        if (response.code == Status.OK) this.shell.println(response.getBody());
        else this.shell.error(response.getBody());
    }

    private void processInput(String[] tokens) {
        try {
            Pair<Command, CommandArgument[]> command = this.parser.parse(tokens);
            UserManager.processAuth(command);
            this.requestCallback.call(new Request(command.a, command.b, UserManager.getClient()));
        } catch (Exception e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }

    @Override
    public Callback<Response> getInterface() {
        return this::processOutput;
    }

    @Override
    public void setRequestCallback(Callback<Request> callback) {
        this.requestCallback = callback;
    }
}