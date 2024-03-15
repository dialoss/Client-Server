package Server.Commands.List;

import Common.Stringify;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Storage.Collection.CollectionManager;

public class Help extends Command {
    public Help() {
        super("help", "Displays help on available commands");
    }


    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return new Stringify(CommandManager.getAll().values().toArray()).get();
    }
}