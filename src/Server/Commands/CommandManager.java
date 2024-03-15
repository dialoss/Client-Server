package Server.Commands;

import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.User;
import Server.Commands.List.*;
import Server.Internal.PasswordManager;
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
        User user = request.getClient();
        Response response = null;

        try {
            if (command.getName().equals("login")) {
                if (!PasswordManager.login(user))
                    response = new Response("Invalid login/password combintion.", Status.FORBIDDEN, user);
                else
                    response = new Response("You are successfully login.", Status.OK, user);
            } else if (command.getName().equals("register")) {
                if (PasswordManager.login(user))
                    response = new Response("You has already registered.", Status.OK, user);
                else
                    response = new Response("You are successfully registered.", Status.OK, PasswordManager.register(request));
            } else if (!PasswordManager.login(user)) {
                response = new Response("Forbidden. You must login before using this app.", Status.FORBIDDEN, user);
            } else {
                String result = command.execute(StorageConnector.manager, request.getArguments());
                response = new Response(result, Status.OK, request.getClient());
            }
        } catch (Exception e) {
            response = new Response(e.toString(), Status.SERVER_ERROR, request.getClient());
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
        add(GetField.class);
        add(Exit.class);
        add(Login.class);
        add(Register.class);
    }
}