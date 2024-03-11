package Server.Commands;

import Server.Commands.List.CommandArgument;

public abstract class ServerCommand extends Command implements IServerExecutable {
    protected ServerCommand(String name, String description) {
        super(name, description);
    }
    protected ServerCommand(String name, String description, CommandArgument[] arguments) {
        super(name, description, arguments);
    }
}

