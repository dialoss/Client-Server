package Server.Commands.List;

import Common.Commands.Command;
import Common.Connection.UserClient;
import Common.Stringify;
import Server.Commands.CommandManager;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Help extends Command {
    public Help() {
        super("help", "Displays help on available commands");
    }


    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) {
        return new Stringify(CommandManager.getAll().values().toArray()).get();
    }
}