package Server.Commands.List;

import Server.Commands.Command;
import Server.Models.Organization;
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
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        Organization item = (Organization) args[1].getValue();
        Integer id = (Integer) args[0].getValue();
        collectionManager.update(id, item);
        return "Item updated";
    }
}