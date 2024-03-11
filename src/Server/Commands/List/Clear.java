package Server.Commands.List;

import Server.Commands.Command;
import Server.Commands.ServerCommand;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Clear extends ServerCommand {
    public Clear() {
        super("clear", "Очищает коллекцию");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        collectionManager.clear();
        return "";
    }
}
