package Client;

import Common.EventBus.Callback;
import Common.EventBus.EventBus;
import Server.CommandManager;
import Server.Commands.ClientCommand;
import Server.Commands.List.CommandArgument;
import Server.Request;
import Server.Response;
import exceptions.CommandNotFound;

import java.lang.invoke.TypeDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientCommandManager {
    Map<String, ClientCommand> commands;
    List<ClientCommand> history;

    ClientCommandManager(Callback callback) {
        EventBus.on("response", (Object data) -> {
            Response response = (Response) data;
            callback.call(response.getBody());
        });
        this.commands = CommandManager.getClientCommands();
        this.history = new ArrayList<>();
    }

    public void execute(String commandName, Shell shell) throws CommandNotFound {
        ClientCommand command = this.commands.get(commandName);
        if (command == null) throw new CommandNotFound();
        for (CommandArgument arg : command.getArguments()) {
            Object value = arg.type.cast(new Form(arg, shell).get());
        }
        this.request(new Request(commandName, new CommandArgument[]{}));
        this.history.add(command);
    }

    private void request(Request request) {
        EventBus.emit("request", request);
    }
}
