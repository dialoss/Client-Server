package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Register extends Command {
    public Register() {
        super("register", "Register user", new CommandArgument[]{
                new CommandArgument("login", String.class),
                new CommandArgument("password", String.class),
                new CommandArgument("repeat_password", String.class)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return "";
    }
}