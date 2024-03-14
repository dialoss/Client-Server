package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Exit extends Command {
    public Exit() {
        super("exit", "Exits the program (without saving to a file)");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        System.exit(0);
        return "";
    }
}