package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

public class Load extends Command {
    public Load() {
        super("load", "Считывает коллекцию из файла",
                new CommandArgument[]{new CommandArgument("filename", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String filename = (String) args[0].getValue();
        StorageConnector.load(filename);
        return "Коллекция загружена";
    }
}
