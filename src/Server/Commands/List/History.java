package Server.Commands.List;

import Server.Commands.Command;
import Server.Commands.CommandExecutor;
import Server.Commands.HistoryEntry;
import Server.Storage.Collection.CollectionManager;

public class History extends Command {
    public History() {
        super("history", "Displays command history");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        StringBuilder b = new StringBuilder();
        CommandExecutor.getHistory().stream()
                .map((HistoryEntry h) -> h.request.getName() + " Status " + h.response.code + "\n")
                .forEach(b::append);
        return b.toString();
    }
}