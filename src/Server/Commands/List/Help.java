package Server.Commands.List;

import Client.CommandLineInterface;
import Common.Tools;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Commands.List.CommandArgument;
import Server.Storage.CollectionManager;

public class Help extends Command {
    public Help() {
        super("help", "¬ыводит справку по доступным командам");
    }


    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return Tools.itemsToString(CommandManager.getAll().values().toArray());
    }
}
