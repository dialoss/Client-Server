package Client;

import Client.Shell.IForm;
import Client.Shell.IOdevice;
import Common.EventBus.Callback;
import Server.Connection.Request;
import Server.Connection.Response;

public class CommandLineInterface implements UserInterface {
    private final IOdevice shell;
    private Callback<Request> requestCallback;
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

    private void processInput(String[] tokens) {
        try {
            this.requestCallback.call(this.parser.parse(tokens));
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