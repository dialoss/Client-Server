package Server.Commands;

import Common.Commands.Command;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
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
        UserManager.setClient(request.getClient());
        if (UserManager.getClientId() == -1)
            return new Response("Forbidden. You must login before using this app.", Status.FORBIDDEN);

        Response response = null;
        try {
            Command command = CommandManager.get(request.getCommandName());
            Object result = command.execute(StorageConnector.manager, request.getArguments());
            if (Response.class.isAssignableFrom(result.getClass())) response = (Response) result;
            else response = new Response(result, Status.OK);
        } catch (Exception e) {
            response = new Response(e.toString(), Status.SERVER_ERROR);
        }
        response.setUserClient(UserManager.getClient());
        history.add(new HistoryEntry(request, response));
        System.out.println(response.getBody());
        return response;
    }
}
