package Server.Connection;

import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

public class Request {
    private final CommandArgument[] arguments;
    private final Command command;

    public Request(Command command, CommandArgument[] arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getName() {
        return this.command.getName();
    }

    public CommandArgument[] getArguments() {
        return this.arguments;
    }
}
