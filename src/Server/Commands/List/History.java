package Server.Commands.List;

import Common.Commands.Command;
import Server.Commands.CommandExecutor;
import Server.Commands.HistoryEntry;
import Server.Storage.Collection.CollectionManager;

public class History extends Command {
    public History() {
        super("history", "Displays command history");
    }

    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        StringBuilder b = new StringBuilder();
        CommandExecutor.getHistory().stream()
                .map((HistoryEntry h) -> h.request.getCommandName() + " Status " + h.response.code + "\n")
                .forEach(b::append);
        return b.toString();
    }
}