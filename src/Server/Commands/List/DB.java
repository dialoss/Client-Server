package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Server.Commands.CommandManager;
import Server.Storage.Collection.CollectionManager;

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
            return (Response) CommandManager.get("load").execute(null, null);
        }
        return null;
    }
}