package Client;

import Client.Shell.*;
import Common.EventBus.Callback;
import Common.Exceptions.CommandNotFound;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Commands.List.CommandArgument;
import Server.Connection.Response;
import Server.Connection.Request;
import java.util.Arrays;

public class CommandLineInterface implements UserInterface {
    private final IForm shellForm;
    private final IOdevice shell;
    private Callback<Request> APICallback;

    public CommandLineInterface(IForm shellForm) {
        this.shell = shellForm.device;
        this.shellForm = shellForm;
    }

    private CommandArgument[] getArguments(Command command) {
        CommandArgument[] arguments = Arrays.copyOf(command.getArguments(), command.getArguments().length);
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            Object value = new Form(arg, this.shellForm).get();
            arguments[i++].setValue(value);
        }
        return arguments;
    }

    private void execute(String command) {
        Command cmd = CommandManager.get(command);
        if (cmd == null) throw new CommandNotFound();
        CommandArgument[] arguments = this.getArguments(cmd);
        Request r = new Request(cmd, arguments);
        this.APICallback.call(r);
    }

    private void processOutput(Response response) {
        this.shell.println(response.getBody());
    }

    private void processInput(String commandName) {
        try {
            this.execute(commandName);
        } catch (RuntimeException e) {
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
        this.APICallback = callback;
    }
}
