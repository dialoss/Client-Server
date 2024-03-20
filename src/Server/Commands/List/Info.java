package Server.Commands.List;

import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Info extends Command {
    public Info() {
        super("info", "Displays information about the collection (type, initialization date, number of elements, etc.)");
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args) {
        return collectionManager.info.getInfo();
    }
}