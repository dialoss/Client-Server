package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.UserClient;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Arrays;
import java.util.Map;

public class Get extends Command {
    public Get() {
        super("get", "Returns collection from server to client",
                new CommandArgument[]{
                        new CommandArgument("amount", Integer.class)
                }
        );
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) throws Exception {
        Integer amount = (Integer) args.get("amount");
        return new Response(Arrays.copyOfRange(StorageConnector.manager.getAll(), 0, amount), Status.OK)
                .withMessage("Collection returned with %s items".formatted(amount));
    }
}