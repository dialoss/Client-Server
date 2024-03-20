package Server.Commands.List;

import Common.Commands.ArgumentPosition;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

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
    public String execute(CollectionManager collectionManager, Map<String, Object> args) {
        Organization item = (Organization) args.get("element");
        Integer id = (Integer) args.get("id");
        collectionManager.update(id, item);
        return "Item updated";
    }
}