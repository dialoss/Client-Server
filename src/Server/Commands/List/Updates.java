package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.UserClient;
import Server.ConnectionManagers.Notification;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Updates extends Command {
    public Updates() {
        super("updates", "Checks whether user need update of collection on client",
                new CommandArgument[]{});
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) {
        return new Response(Notification.get(client.port), Status.OK);
    }
}