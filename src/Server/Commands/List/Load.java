package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

public class Load extends Command {
    public Load() {
        super("load", "Reads a collection from a storage",
                new CommandArgument[]{
//                        new CommandArgument("filename", String.class).withNotRequired("data.json")
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) throws Exception {
//        String filename = (String) args[0].getValue();
        StorageConnector.loadDB();
        return "Collection loaded";
    }
}