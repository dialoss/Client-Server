package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

public class Save extends Command {
    public Save() {
        super("save", "Saves the collection to a file",
                new CommandArgument[]{
                        new CommandArgument("filename", String.class).withNotRequired("data.json")
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String filename = (String) args[0].getValue();
        String path = StorageConnector.save(filename);
        return "The file is saved at the path: " + path;
    }
}