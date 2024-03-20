package Server.Commands.List;

import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Clear extends Command {
    public Clear() {
        super("clear", "Clears the collection");
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args) {
        collectionManager.clear();
        return "Collection cleared";
    }
}