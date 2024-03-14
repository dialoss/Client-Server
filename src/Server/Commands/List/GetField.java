package Server.Commands.List;

import Common.Tools;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class GetField extends Command {
    public GetField() {
        super("get_field_sorted",
                "Выводит значения поля field всех элементов в заданном порядке",
                new CommandArgument[]{
                        new CommandArgument("field", String.class),
                        new CommandArgument("order", Integer.class)
                                .withValues(new Integer[]{0, 1})
        });
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        String fieldName = (String) args[0].getValue();
        Integer order = (Integer) args[1].getValue();

        return Tools.stringify(new Query(manager.getAll()).reduce(fieldName).sorted(order));
    }
}
