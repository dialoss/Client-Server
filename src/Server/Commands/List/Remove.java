package Server.Commands.List;

import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Remove extends Command {
    public Remove() {
        super("remove", "Удаляет элемент из коллекции по его ключу",
                new CommandArgument[]{new CommandArgument("id", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        Integer id = (Integer) args[0].getValue();
        collectionManager.delete(id);
        return String.format("Элемент %s удалён", id);
    }
}
