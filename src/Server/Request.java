package Server;

import Server.Commands.List.CommandArgument;

public class Request {
    private final CommandArgument[] arguments;
    private final String name;

    public Request(String name, CommandArgument[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return this.name;
    }

    public CommandArgument[] getArguments() {
        return this.arguments;
    }
}
