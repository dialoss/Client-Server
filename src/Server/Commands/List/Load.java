package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

public class Load extends Command {
    public Load() {
        super("load", "Reads a collection from a file",
                new CommandArgument[]{
                        new CommandArgument("filename", String.class).withNotRequired("data.json")
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String filename = (String) args[0].getValue();
        StorageConnector.load(filename);
        return "Collection loaded";
    }
}