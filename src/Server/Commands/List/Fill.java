package Server.Commands.List;

import Common.Commands.CommandArgument;
import Common.Commands.Command;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

public class Fill extends Command {
    public Fill() {
        super("fill", "Fills the collection with random values",
                new CommandArgument[]{new CommandArgument("amount", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        Integer amount = (Integer) args[0];
        for (int i = 0; i < amount; i++) {
            collectionManager.insert((Organization) new Organization().random());
        }
        return String.format("%s elements added", amount);
    }
}