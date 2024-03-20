package Server.Commands;

import Common.Commands.Command;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Exceptions.CommandNotFound;
import Server.Internal.UserManager;
import Server.Storage.StorageConnector;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private static final List<HistoryEntry> history = new ArrayList<>();

    public static List<HistoryEntry> getHistory() {
        return history;
    }

    static public Response execute(Request request) {
        if (request.getArgument("Authorization") == null)
            return new Response("Forbidden. You must login before using this app.", Status.FORBIDDEN);

        Command command = CommandManager.get(request.getCommandName());
        if (command == null) throw new CommandNotFound();

        UserManager.setClient(request.getClient());
        Response response = null;
        try {
            Object result = command.execute(StorageConnector.manager, request.getArguments());
            if (Response.class.isAssignableFrom(result.getClass())) response = (Response) result;
            else {
                if (result.getClass().isAssignableFrom(String.class)) response = new Response((String) result, Status.OK);
                else response = new Response(result, Status.OK);
            }
        } catch (Exception e) {
            response = new Response(e.getMessage(), Status.SERVER_ERROR);
        }
        response.setUserClient(UserManager.getClient());
        history.add(new HistoryEntry(request, response));
        System.out.println(response.getMessage());
        return response;
    }
}
