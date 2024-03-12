package Server.Commands.List;

import Server.Commands.HistoryEntry;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Storage.CollectionManager;

public class History extends Command {
    public History() {
        super("history", "Выводит историю команд");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        StringBuilder b = new StringBuilder();
        CommandManager.getHistory().stream()
                .map((HistoryEntry h) -> h.request.getName() + " Статус " + h.response.code + "\n")
                .forEach(b::append);
        return b.toString();
    }
}
