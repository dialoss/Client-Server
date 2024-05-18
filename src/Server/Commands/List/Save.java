package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Map;

public class Save extends Command {
    public Save() {
        super("save", "Saves the collection to a storage",
                new CommandArgument[]{
                        new CommandArgument("filename", String.class).withNotRequired("data.json")
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args) throws Exception {
//        String filename = (String) args.get("filename");
//        String path = StorageConnector.fileStorage.write(
//                Tools.stringify(StorageConnector.manager.getAll()), filename);
        StorageConnector.saveDB();
        return "Collection saved";
//        return "The file is saved at the path: " + path;
    }
}