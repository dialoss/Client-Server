package Server.Commands.List;

import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Insert extends Command {
    public Insert() {
        super("insert", "Добавляет новый элемент с заданным ключом",
                new CommandArgument[]{new CommandArgument("element", Organization.class, ArgumentPosition.COMPLEX)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        collectionManager.insert((Organization) args[0].getValue());
        return "Элемент добавлен";
    }
}
