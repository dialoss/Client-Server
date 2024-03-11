package Server;

import Server.Commands.*;
import Server.Commands.List.*;
import Server.Storage.CollectionManager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, ClientCommand> clientCommands = new HashMap<>();
    protected static final CollectionManager collectionManager = new CollectionManager();

    static public void add(Class commandName) {
        try {
            Command command = (Command) commandName.getDeclaredConstructor().newInstance();
            CommandManager.commands.put(command.getName(), command);
            CommandManager.clientCommands.put(command.getName(), new ClientCommand(command.getName(), command.description, command.getArguments()));
        } catch (Exception e) {
        }
    }

    static public Map<String, Command> get() {
        return CommandManager.commands;
    }

    static public Map<String, ClientCommand> getClientCommands() {
        return CommandManager.clientCommands;
    }

    static public Response execute(Request request) {
        Command command = commands.get(request.getName());
        try {
            String result = command.execute(CommandManager.collectionManager, request.getArguments());
            return new Response(result, Status.OK);
        } catch (Exception e) {
            return new Response(e.getMessage(), Status.SERVER_ERROR);
        }
    }

    static class CommandManagerBuilder {
        public static void build() {
            CommandManager.add(Info.class);
            CommandManager.add(Show.class);
            CommandManager.add(Insert.class);
            CommandManager.add(Help.class);
            CommandManager.add(Filter.class);
            CommandManager.add(ExecuteScript.class);
        }
    }

    static {
        CommandManagerBuilder.build();
    }
}