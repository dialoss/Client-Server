package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Login extends Command {
    public Login() {
        super("login", "Login user",
                new CommandArgument[]{
                        new CommandArgument("login", String.class),
                        new CommandArgument("password", String.class)
                });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return "";
    }
}