package Client;

import Client.Shell.Shell;
import Common.EventBus.Callback;
import Common.EventBus.EventBus;
import Common.Form;
import Server.Commands.CommandManager;
import Server.Commands.ClientCommand;
import Server.Commands.List.CommandArgument;
import Server.Models.Organization;
import Server.Request;
import Server.Response;
import exceptions.CommandNotFound;
import org.json.simple.JSONObject;

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
        CommandArgument[] arguments = Arrays.copyOf(command.getArguments(), command.getArguments().length);
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            Object value = new Form(arg, shell).get();
            if (value instanceof JSONObject) {
                value = new Organization().from((JSONObject) value, false);
            }
            arguments[i++].setValue(value);
        }
        this.request(new Request(commandName, arguments));
        this.history.add(command);
    }

    private void request(Request request) {
        EventBus.emit("request", request);
    }
}
