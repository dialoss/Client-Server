package Server;

import Server.Commands.*;
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
            CommandManager.clientCommands.put(command.getName(), new ClientCommand(command.getName(), ""));
        } catch (Exception e) {
        }
    }

    static public Map<String, Command> get() {
        return CommandManager.commands;
    }

    static public Map<String, ClientCommand> getClientCommands() {
        return CommandManager.clientCommands;
    }

    static public Response execute(String commandName) {
        Command command = commands.get(commandName);
        return new Response(command.execute(CommandManager.collectionManager));
    }
}

class CommandManagerBuilder {
    public static void build() {
       CommandManager.add(Info.class);
       CommandManager.add(Show.class);
       CommandManager.add(Insert.class);
       CommandManager.add(Help.class);
        CommandManager.add(Filter.class);
    }
}