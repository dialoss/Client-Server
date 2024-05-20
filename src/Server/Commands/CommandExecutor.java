package Server.Commands;

import Common.Commands.Command;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Exceptions.CommandNotFound;
import Common.Exceptions.ForbiddenException;
import Server.Storage.StorageConnector;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private static final List<HistoryEntry> history = new ArrayList<>();

    public static List<HistoryEntry> getHistory() {
        return history;
    }

    static public Response execute(Request request) {
//        if (request.getHeader("Authorization") == null)
//            return new Response("Forbidden. You must login before using this app.", Status.FORBIDDEN);

        Command command = CommandManager.get(request.getCommandName());
        if (command == null) throw new CommandNotFound();
        Response response;
        try {
            Object result = command.execute(StorageConnector.manager, request.getArguments(), request.getClient());
            if (Response.class.isAssignableFrom(result.getClass())) response = (Response) result;
            else {
                if (result.getClass().isAssignableFrom(String.class)) response = new Response((String) result, Status.OK);
                else response = new Response(result, Status.OK);
            }
        } catch (ForbiddenException e) {
            response = new Response(e.getMessage(), Status.FORBIDDEN);
        }
        catch (Exception e) {
            response = new Response(e.getMessage(), Status.SERVER_ERROR);
        }

        history.add(new HistoryEntry(request, response));
        System.out.println(request.getCommandName());
        return response;
    }
}
