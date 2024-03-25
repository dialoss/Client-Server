package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Map;

public class Load extends Command {
    public Load() {
        super("load", "Reads a collection from a storage",
                new CommandArgument[]{
                        new CommandArgument("filename", String.class).withNotRequired("data.json")
        });
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args) throws Exception {
        String filename = (String) args.get("filename");
        StorageConnector.loadFile(filename);
        return new Response("Collection loaded with %s items".formatted(StorageConnector.manager.getSize()), Status.OK);
    }
}