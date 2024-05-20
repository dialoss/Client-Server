package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.UserClient;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Map;

public class Unload extends Command {
    public Unload() {
        super("unload", "Clears runtime collection",
                new CommandArgument[]{});
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) throws Exception {
        StorageConnector.manager.clear();
        return "Collection unloaded";
    }
}