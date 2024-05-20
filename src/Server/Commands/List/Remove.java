package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.UserClient;
import Common.Exceptions.ElementNotFound;
import Common.Exceptions.ForbiddenException;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;
import java.util.Objects;

public class Remove extends Command {
    public Remove() {
        super("remove", "Removes an element from the collection by its id",
                new CommandArgument[]{new CommandArgument("id", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) {
        Integer id = (Integer) args.get("id");

        Organization element = collectionManager.get(id);
        if (element == null) throw new ElementNotFound(id);
        if (!Objects.equals(element.useraccount_id, client.getId())) throw new ForbiddenException();
        collectionManager.delete(id);
        return "Item %s has been removed.".formatted(id);
    }
}