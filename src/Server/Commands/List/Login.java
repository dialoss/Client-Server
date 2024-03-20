package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.UserClient;
import Server.Internal.PasswordManager;
import Server.Internal.UserManager;
import Server.Storage.Collection.CollectionManager;

import java.sql.SQLException;
import java.util.Map;

public class Login extends Command {
    public Login() {
        super("login", "Login user",
                new CommandArgument[]{
                        new CommandArgument("login", String.class),
                        new CommandArgument("password", String.class)
                });
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args) throws SQLException {
        UserClient user = UserManager.getClient();
        if (!PasswordManager.login(user))
            return new Response("Invalid login/password combination.", Status.FORBIDDEN);
        else
            return new Response("You are successfully login.", Status.OK);
    }
}