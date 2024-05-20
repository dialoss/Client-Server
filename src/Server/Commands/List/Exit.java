package Server.Commands.List;

import Common.Commands.Command;
import Common.Connection.UserClient;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Exit extends Command {
    public Exit() {
        super("exit", "Exits the program (without saving to a file)");
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) {
//        System.exit(0);
        return "Exiting is disabled from client";
    }
}