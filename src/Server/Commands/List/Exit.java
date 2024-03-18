package Server.Commands.List;

import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Exit extends Command {
    public Exit() {
        super("exit", "Exits the program (without saving to a file)");
    }

    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        System.exit(0);
        return "";
    }
}