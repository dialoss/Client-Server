package Client;

import Client.Shell.IForm;
import Client.Shell.IOdevice;
import Common.Connection.Status;
import Common.EventBus.Callback;
import Common.Connection.Request;
import Common.Connection.Response;

public class CommandLineInterface implements UserInterface {
    private final IOdevice shell;
    private Callback<Request> requestCallback;
    private final CommandParser parser;

    public CommandLineInterface(IForm shellForm) {
        this.shell = shellForm.device;
        this.parser = new CommandParser(shellForm);
    }

    private void processOutput(Response response) {
        if (response.code == Status.OK) this.shell.println(response.getBody());
        else this.shell.error(response.getBody());
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