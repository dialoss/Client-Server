package Server.Commands;

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
        Command command = CommandManager.get(request.getName());
        Response response = null;

        try {
            Response authResponse = UserManager.processAuth(command, request);
            if (authResponse != null) return authResponse;

            String result = command.execute(StorageConnector.manager, request.getArguments());
            response = new Response(result, Status.OK, request.getClient());
        } catch (Exception e) {
            response = new Response(e.toString(), Status.SERVER_ERROR, request.getClient());
        }
        history.add(new HistoryEntry(request, response));
        return response;
    }
}
