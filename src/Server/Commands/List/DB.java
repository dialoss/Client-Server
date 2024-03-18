package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

public class DB extends Command {
    public DB() {
        super("db", "Request to the database",
                new CommandArgument[]{
                        new CommandArgument("action", String.class),
                });
    }

    @Override
    public Response execute(CollectionManager collectionManager, Object[] args) throws Exception {
        if ("getAll".equals(args[0])) {
            return new Response(StorageConnector.dbManager.getSession().getAll(Organization.class), Status.OK);
        }
        return null;
    }
}