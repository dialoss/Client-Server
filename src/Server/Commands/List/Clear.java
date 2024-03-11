package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Clear extends Command {
    public Clear() {
        super("clear", "Очищает коллекцию");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        collectionManager.clear();
        return "";
    }
}
