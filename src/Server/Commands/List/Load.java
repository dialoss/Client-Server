package Server.Commands.List;

import Common.Commands.CommandArgument;
import Common.Commands.Command;
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
    public String execute(CollectionManager collectionManager, Object[] args) throws Exception {
//        String filename = (String) args[0];
        StorageConnector.loadDB();
        return "Collection loaded";
    }
}