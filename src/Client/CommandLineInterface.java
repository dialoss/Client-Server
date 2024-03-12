package Client;

import Client.Shell.*;
import Common.EventBus.Callback;
import Server.Connection.Response;
import Server.Connection.Request;

public class CommandLineInterface implements UserInterface {
    private final IOdevice shell;
    private Callback<Request> APICallback;
    private final CommandParser parser;

    public CommandLineInterface(IForm shellForm) {
        this.shell = shellForm.device;
        this.parser = new CommandParser(shellForm);
    }

    private void processOutput(Response response) {
        switch (response.code) {
            case SERVER_ERROR -> this.shell.error(response.getBody());
            case OK -> this.shell.println(response.getBody());
        }
    }

    private void processInput(String commandName) {
        try {
            this.APICallback.call(this.parser.parse(commandName));
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
