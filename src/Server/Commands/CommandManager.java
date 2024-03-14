package Server.Commands;

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
    }
}