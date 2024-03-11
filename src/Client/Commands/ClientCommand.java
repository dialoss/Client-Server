package Client.Commands;

import Client.Commands.IClientExecutable;
import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

public abstract class ClientCommand extends Command implements IClientExecutable {
    public ClientCommand(String name, String description) {
        super(name, description);
    }

    public ClientCommand(String name, String description, CommandArgument[] arguments) {
        super(name, description, arguments);
    }
}
