package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Map;

public class DB extends Command {
    public DB() {
        super("db", "Request to the database",
                new CommandArgument[]{
                        new CommandArgument("action", String.class),
                });
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args) throws Exception {
        if ("getAll".equals(args.get("action"))) {
            return new Response(StorageConnector.manager.getAll(), Status.OK);
        }
        return null;
    }
}