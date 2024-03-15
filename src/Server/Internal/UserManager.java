package Server.Internal;

import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.UserClient;
import Server.Commands.Command;
import Server.Models.MObject;
import Server.Models.UserAccount;
import Server.Storage.StorageConnector;

public class UserManager {
    private static int clientId = -1;
    private static UserClient client = new UserClient();

    public static void setClient(UserClient client) {
        UserManager.client = client;
    }

    public static Response processAuth(Command command, Request request) throws Exception {
        UserClient user = request.getClient();
        Response response = null;
        if (command.getName().equals("login")) {
            if (!PasswordManager.login(user))
                response = new Response("Invalid login/password combination.", Status.FORBIDDEN, user);
            else
                response = new Response("You are successfully login.", Status.OK, user);
        } else if (command.getName().equals("register")) {
            if (PasswordManager.getUser(user) != null)
                response = new Response("You has already registered.", Status.OK, user);
            else {
                user = PasswordManager.register(request);
                response = new Response("You are successfully registered.", Status.OK, user);
            }
        } else if (clientId == -1 && !PasswordManager.login(user)) {
            response = new Response("Forbidden. You must login before using this app.", Status.FORBIDDEN, user);
        }
        setClient(user);
        if (clientId == -1) {
            MObject dbUser = StorageConnector.dbManager.getSession()
                    .get(UserAccount.class, "login='%s'".formatted(user.getLogin()))[0];
            setClientId((Integer) dbUser.get("id"));
        }
        return response;
    }

    public static UserClient getClient() {
        return client;
    }

    public static void setClientId(int clientId) {
        UserManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
