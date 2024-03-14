package Server.Commands.List;

import Common.Tools;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Ascending extends Command {
    public Ascending() {
        super("print_field_ascending",
                "Выводит значения поля field всех элементов в порядке возрастания",
                new CommandArgument[]{new CommandArgument("field", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        String fieldName = (String) args[0].getValue();

        return Tools.stringify(new Query(manager.getAll()).reduce(fieldName).sorted());
    }
}
