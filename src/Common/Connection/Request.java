package Common.Connection;

import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

import java.io.Serializable;

public class Request implements Serializable {
    private final CommandArgument[] arguments;
    private final Command command;
    private final UserClient userClient;

    public Request(Command command, CommandArgument[] arguments) {
        this.command = command;
        this.arguments = arguments;
        this.userClient = null;
    }

    public Request(Command command, CommandArgument[] arguments, UserClient userClient) {
        this.command = command;
        this.arguments = arguments;
        this.userClient = userClient;
    }

    public UserClient getClient() {
        return userClient;
    }

    public String getName() {
        return this.command.getName();
    }

    public CommandArgument[] getArguments() {
        return this.arguments;
    }
}