package Server.Commands.List;

import Common.Commands.Command;
import Common.Connection.Response;
import Common.Connection.Status;
import Server.Commands.CommandExecutor;
import Server.Commands.HistoryEntry;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class History extends Command {
    public History() {
        super("history", "Displays command history");
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args) {
        StringBuilder b = new StringBuilder();
        CommandExecutor.getHistory().stream()
                .map((HistoryEntry h) -> h.request.getCommandName() + " Status " + h.response.code + "\n")
                .forEach(b::append);
        return new Response(CommandExecutor.getHistory().stream()
                .map((HistoryEntry h) -> h.request.getCommandName() + " Status " + h.response.code)
                .toArray(),
                Status.OK);
    }
}