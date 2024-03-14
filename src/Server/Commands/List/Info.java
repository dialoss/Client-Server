package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Info extends Command {
    public Info() {
        super("info", "Displays information about the collection (type, initialization date, number of elements, etc.)");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return collectionManager.info.getInfo();
    }
}