package Server.Commands.List;

import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }

    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        collectionManager.clear();
        return "Collection cleared";
    }
}