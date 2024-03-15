package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        collectionManager.clear();
        return "Collection cleared";
    }
}