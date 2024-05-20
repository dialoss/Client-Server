package Server.Commands.List;

import Client.GUI.UserInfo;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Connection.UserClient;
import Common.Models.MObject;
import Common.Models.UserAccount;
import Server.Internal.PasswordManager;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.Database.DBOperations;
import Server.Storage.StorageConnector;
import me.xdrop.jrand.JRand;

import java.util.Map;

public class Register extends Command {
    public Register() {
        super("register", "Register Client", new CommandArgument[]{
                new CommandArgument("name", String.class),
                new CommandArgument("login", String.class),
                new CommandArgument("password", String.class),
                new CommandArgument("repeat_password", String.class)
        });
    }

    @Override
    public Response execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) throws Exception {
        if (PasswordManager.getUser(client) != null)
            return new Response("You has already registered.", Status.OK);
        else {
            String login = (String) args.get("login");
            String password = (String) args.get("password");
            String name = (String) args.get("name");
            String salt = JRand.string().range(90, 100).gen();
            String encrypted = PasswordManager.encrypt(password, salt);

            DBOperations session = StorageConnector.dbManager.getSession();
            int key = session.insert(UserAccount.class, new UserAccount().from(new MObject(Map.of(
                    "login", login,
                    "password", encrypted,
                    "salt", salt,
                    "name", name
            ))));
            Response r = new Response("You are successfully registered.", Status.OK);
            r.setUserClient(new UserClient(new UserInfo(name, login, password)).withId(key));
            return r;
        }
    }
}