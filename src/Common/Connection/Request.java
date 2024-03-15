package Common.Connection;

import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

import java.io.Serializable;

public class Request implements Serializable {
    private final CommandArgument[] arguments;
    private final Command command;
    private final User client;

    public Request(Command command, CommandArgument[] arguments) {
        this.command = command;
        this.arguments = arguments;
        this.client = null;
    }

    public Request(Command command, CommandArgument[] arguments, User client) {
        this.command = command;
        this.arguments = arguments;
        this.client = client;
    }

    public User getClient() {
        return client;
    }

    public String getName() {
        return this.command.getName();
    }

    public CommandArgument[] getArguments() {
        return this.arguments;
    }
}