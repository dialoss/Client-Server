package Client;

import Common.EventBus.Callback;
import Common.EventBus.EventBus;
import Server.CommandManager;
import Server.Commands.ClientCommand;
import Server.Commands.IRequestCommand;
import Server.Request;
import Server.Response;
import exceptions.CommandNotFound;

import java.util.Map;

public class ClientCommandManager {
    Map<String, ClientCommand> commands;

    ClientCommandManager(Callback callback) {
        EventBus.on("response", callback);
        this.commands = CommandManager.getClientCommands();
    }

    public String execute(String commandName) throws CommandNotFound {
        ClientCommand command = this.commands.get(commandName);
        if (command == null) throw new CommandNotFound();
        if (command instanceof IRequestCommand) {
            return this.request(new Request(commandName));
        }
        return command.getDescription();
    }

    private Response request(Request request) {
        EventBus.emit("request", request);
    }
}
