package Server.Commands;

import Server.Commands.ClientCommand;
import Server.Commands.IExecutable;
import Server.Commands.List.CommandArgument;

public abstract class Command extends ClientCommand implements IExecutable {
    protected Command(String name, String description) {
        super(name, description, new CommandArgument[]{});
    }
    protected Command(String name, String description, CommandArgument[] arguments) {
        super(name, description, arguments);
    }
}

