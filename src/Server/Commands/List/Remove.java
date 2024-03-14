package Server.Commands.List;

import Common.Exceptions.ElementNotFound;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Remove extends Command {
    public Remove() {
        super("remove", "Removes an element from the collection by its key",
                new CommandArgument[]{new CommandArgument("id", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        Integer id = (Integer) args[0].getValue();
        if (collectionManager.get(id) == null) throw new ElementNotFound(id);
        collectionManager.delete(id);
        return "Item %s has been removed.".formatted(id);
    }
}