package Server.Commands.List;

import Common.Commands.ArgumentPosition;
import Common.Commands.CommandArgument;
import Common.Commands.Command;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

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
    public String execute(CollectionManager collectionManager, Object[] args) {
        Organization item = (Organization) args[1];
        Integer id = (Integer) args[0];
        collectionManager.update(id, item);
        return "Item updated";
    }
}