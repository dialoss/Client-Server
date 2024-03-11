package Server.Commands.List;

import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Fill extends Command {
    public Fill() {
        super("fill", "Заполняет коллекцию случайными значениями",
                new CommandArgument[]{new CommandArgument("amount", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        Integer amount = (Integer) args[0].getValue();
        for (int i = 0; i < amount; i++) {
            collectionManager.insert(new Organization());
        }
        return String.format("%s элементов добавлено", amount);
    }
}
