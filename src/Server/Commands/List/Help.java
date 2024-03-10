package Server.Commands.List;

import Server.CommandManager;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Help extends Command {
    public Help() {
        super("help", "¬ыводит справку по доступным командам");
    }


    @Override
    public String execute(CollectionManager collectionManager, String[] args) {
        String info = "";
        for (Command command : CommandManager.get().values()) {
            info = info.concat(command.getDescription() + "\n");
        }
        return info;
    }
}
