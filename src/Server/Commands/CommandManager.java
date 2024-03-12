package Server.Commands;

import Server.Commands.List.ExecuteScript;
import Server.Commands.List.Help;
import Server.Commands.List.History;
import Server.Commands.List.*;
import Server.Connection.Request;
import Server.Connection.Response;
import Server.Connection.Status;
import Server.Storage.StorageConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final List<HistoryEntry> history = new ArrayList<>();

    public static List<HistoryEntry> getHistory() {
        return history;
    }

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

    static public Response execute(Request request) {
        Command command = commands.get(request.getName());
        Response response;
        try {
            String result = command.execute(StorageConnector.manager, request.getArguments());
            response = new Response(result, Status.OK);
        } catch (Exception e) {
            response = new Response(e.getMessage(), Status.SERVER_ERROR);
        }
        history.add(new HistoryEntry(request, response));
        return response;
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
            CommandManager.add(Remove.class);
            CommandManager.add(Load.class);
        }
    }

    static {
        CommandManagerBuilder.build();
    }
}