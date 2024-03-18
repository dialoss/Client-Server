package Server.Commands.List;

import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Info extends Command {
    public Info() {
        super("info", "Displays information about the collection (type, initialization date, number of elements, etc.)");
    }

    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        return collectionManager.info.getInfo();
    }
}