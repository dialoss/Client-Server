package Server.Commands.List;

import Common.Stringify;
import Common.Commands.Command;
import Server.Commands.CommandManager;
import Server.Storage.Collection.CollectionManager;

public class Help extends Command {
    public Help() {
        super("help", "Displays help on available commands");
    }


    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        return new Stringify(CommandManager.getAll().values().toArray()).get();
    }
}