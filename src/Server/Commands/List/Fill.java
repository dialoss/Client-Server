package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.UserClient;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Fill extends Command {
    public Fill() {
        super("fill", "Fills the collection with random values",
                new CommandArgument[]{new CommandArgument("amount", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) {
        Integer amount = (Integer) args.get("amount");
        for (int i = 0; i < amount; i++) {
            Organization el = (Organization) new Organization().random();
            el.useraccount_id = client.getId();
            collectionManager.insert(el);
        }
        return String.format("%s elements added", amount);
    }
}