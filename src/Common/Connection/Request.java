package Common.Connection;

import Common.Commands.Command;

import java.io.Serializable;

public class Request implements Serializable {
    private final Command command;
    private UserClient userClient;
    Object[] arguments;

    public Request(String commandName, Object[] arguments) {
        this.command = new Command(commandName);
        this.arguments = arguments;
    }

    public Request(Command command, Object[] arguments) {
        this.command = command;
        this.arguments = arguments;
        this.userClient = null;
    }

    public Request(Command command, Object[] arguments, UserClient userClient) {
        this.command = command;
        this.arguments = arguments;
        this.userClient = userClient;
    }

    public String getCommandName() {
        return this.command.getName();
    }

    public UserClient getClient() {
        return userClient;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }
}