package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

public class Save extends Command {
    public Save() {
        super("save", "Saves the collection to a storage",
                new CommandArgument[]{
//                        new CommandArgument("filename", String.class).withNotRequired("data.json")
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) throws Exception {
//        String filename = (String) args[0].getValue();
//        String path = StorageConnector.dbManager.save();
        StorageConnector.saveDB();
        return "Collection saved";
//        return "The file is saved at the path: " + path;
    }
}