package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Fill extends Command {
    public Fill() {
        super("fill", "Fills the collection with random values",
                new CommandArgument[]{new CommandArgument("amount", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args) {
        Integer amount = (Integer) args.get("amount");
        for (int i = 0; i < amount; i++) {
            collectionManager.insert((Organization) new Organization().random());
        }
        return String.format("%s elements added", amount);
    }
}