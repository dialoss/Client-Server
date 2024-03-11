package Server.Commands;

import Client.Commands.ClientCommand;
import Client.Commands.ExecuteScript;
import Client.Commands.Help;
import Client.Commands.History;
import Server.Commands.List.*;
import Server.Connection.Request;
import Server.Connection.Response;
import Server.Connection.Status;
import Server.Storage.CollectionManager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();
    protected static final CollectionManager collectionManager = new CollectionManager();

    static public void add(Class commandName) {
        try {
            Command cmd = (Command) commandName.getDeclaredConstructor().newInstance();
            if (ServerCommand.class.isAssignableFrom(cmd.getClass())) cmd = (ServerCommand) cmd;
            else cmd = (ClientCommand) cmd;
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

    static public Response execute(Request request) {
        Command command = commands.get(request.getName());
        try {
            String result = ((ServerCommand) command).execute(CommandManager.collectionManager, request.getArguments());
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
            CommandManager.add(Clear.class);
            CommandManager.add(Save.class);
            CommandManager.add(Fill.class);
            CommandManager.add(Update.class);
            CommandManager.add(History.class);
            CommandManager.add(RemoveGreater.class);
            CommandManager.add(Ascending.class);
        }
    }

    static {
        CommandManagerBuilder.build();
    }
}