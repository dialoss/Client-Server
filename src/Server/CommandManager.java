package Server;

import Server.Commands.*;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, ClientCommand> clientCommands = new HashMap<>();

    static public Map<String, Command> get() {
        return CommandManager.commands;
    }

    static public void add(Command command) {
        CommandManager.commands.put(command.name, command);
        CommandManager.clientCommands.put(command.name, new ClientCommand(command.name, command.description));
    }

    static public Map<String, ClientCommand> getClientCommands() {
        return CommandManager.clientCommands;
    }

    static public void execute(String commandName) {
        commands.get(commandName).execute();
    }
}

class CommandManagerBuilder {
    public static void build() {
        CommandManager.add(new Info());
        CommandManager.add(new Show());
        CommandManager.add(new Insert());
        CommandManager.add(new Help());
    }
}