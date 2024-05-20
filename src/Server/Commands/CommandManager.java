package Server.Commands;

import Common.Commands.Command;
import Server.Commands.List.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();

    static public void add(Class<?> commandName) {
        try {
            Command cmd = (Command) commandName.getDeclaredConstructor().newInstance();
            CommandManager.commands.put(cmd.getName(), cmd);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static public Command get(String name) {
        return CommandManager.commands.get(name);
    }

    static public Map<String, Command> getAll() {
        return CommandManager.commands;
    }

    static {
        add(Info.class);
        add(Show.class);
        add(Insert.class);
        add(Help.class);
        add(Filter.class);
        add(ExecuteScript.class);
        add(Clear.class);
        add(Save.class);
        add(Fill.class);
        add(Update.class);
        add(History.class);
        add(RemoveGreater.class);
        add(Ascending.class);
        add(Remove.class);
        add(Load.class);
        add(GetField.class);
        add(Exit.class);
        add(Login.class);
        add(Register.class);
        add(DB.class);
        add(Get.class);
        add(Unload.class);
        add(Updates.class);
    }
}