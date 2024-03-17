package Client;

import Client.Storage.LocalStorage;
import Common.Connection.UserClient;
import Common.Pair;
import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

public class UserManager {
    private static UserClient client;

    public static void init() {
        String[] cookie = LocalStorage.read("cookie").split(" ");
        if (cookie.length == 2) {
            client = new UserClient(cookie[0], cookie[1]);
            System.out.println("Loaded cookie");
        }
        else client = new UserClient();
    }

    public static void setClient(UserClient client) {
        UserManager.client = client;
        LocalStorage.write("cookie", "%s %s".formatted(client.getLogin(), client.getPassword()));
    }

    public static void processAuth(Pair<Command, CommandArgument[]> command) {
        String cmdName = command.a.getName();
        if (!(cmdName.equals("login") || cmdName.equals("register"))) return;
        String login = (String) command.b[0].getValue();
        String password = (String) command.b[1].getValue();
        UserManager.setClient(new UserClient(login, password));
    }

    public static UserClient getClient() {
        return client;
    }
}
