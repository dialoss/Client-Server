package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

public class Save extends Command {
    public Save() {
        super("save", "Сохраняет коллекцию в файл",
                new CommandArgument[]{new CommandArgument("filename", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String filename = (String) args[0].getValue();
        String path = StorageConnector.save(filename);
        return "Файл сохранён по пути: " + path;
    }
}
