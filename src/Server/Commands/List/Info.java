package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Info extends Command {
    public Info() {
        super("info", "Выводит информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return collectionManager.info.getInfo();
    }
}
