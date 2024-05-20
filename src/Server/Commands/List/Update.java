package Server.Commands.List;

import Common.Commands.ArgumentPosition;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.UserClient;
import Common.Exceptions.ElementNotFound;
import Common.Exceptions.ForbiddenException;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;
import java.util.Objects;

public class Update extends Command {
    public Update() {
        super("update", "Updates the value of a collection element whose id is equal to the given one",
                new CommandArgument[]{
                        new CommandArgument("id", Integer.class),
                        new CommandArgument("element", Organization.class)
                                .withPosition(ArgumentPosition.MULTILINE)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) {
        Organization item = (Organization) args.get("element");
        Integer id = (Integer) args.get("id");
        item.id = id;
        Organization element = collectionManager.get(id);
        if (element == null) throw new ElementNotFound(id);
        if (!Objects.equals(element.useraccount_id, client.getId())) throw new ForbiddenException();
        collectionManager.update(id, item);
        return "Item updated";
    }
}