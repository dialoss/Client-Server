package Client;

import Common.Connection.User;
import Common.Pair;
import Server.Commands.Command;
import Server.Commands.List.CommandArgument;

public class UserManager {
    private static User client = new User();

    public static void setClient(User client) {
        UserManager.client = client;
    }

    public static void processAuth(Pair<Command, CommandArgument[]> command) {
        String cmdName = command.a.getName();
        if (!(cmdName.equals("login") || cmdName.equals("register"))) return;
        String login = (String) command.b[0].getValue();
        String password = (String) command.b[1].getValue();
        UserManager.setClient(new User(login, password, (int) (Math.random() * 1e9)));
    }

    public static User getClient() {
        return client;
    }
}
