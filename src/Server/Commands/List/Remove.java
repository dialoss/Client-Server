package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Exceptions.ElementNotFound;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Remove extends Command {
    public Remove() {
        super("remove", "Removes an element from the collection by its id",
                new CommandArgument[]{new CommandArgument("id", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args) {
        Integer id = (Integer) args.get("id");

        if (collectionManager.get(id) == null) throw new ElementNotFound(id);
        collectionManager.delete(id);
        return "Item %s has been removed.".formatted(id);
    }
}