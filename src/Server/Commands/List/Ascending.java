package Server.Commands.List;

import Common.Commands.CommandArgument;
import Common.Stringify;
import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class Ascending extends Command {
    public Ascending() {
        super("print_field_ascending",
                "Displays the field values of all elements in ascending order",
                new CommandArgument[]{new CommandArgument("field", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, Object[] args) {
        String fieldName = (String) args[0];

        return new Stringify(new Query(manager.getAll()).reduce(fieldName).sorted()).get();
    }
}