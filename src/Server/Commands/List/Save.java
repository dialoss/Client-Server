package Server.Commands.List;

import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;
import Server.Storage.JSONStorage;
import org.json.simple.JSONObject;

import java.util.stream.Stream;

public class Save extends Command {
    public Save() {
        super("save", "Сохраняет коллекцию в файл",
                new CommandArgument[]{new CommandArgument("filename", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String path = new JSONStorage((String) args[0].getValue())
                .write(Stream.of(collectionManager.getAll())
                        .map((Organization it) -> it.raw)
                        .toArray(JSONObject[]::new));
        return "Файл сохранён по пути: " + path;
    }
}
