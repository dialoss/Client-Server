package Server.Commands.List;

import Common.Commands.CommandArgument;
import Common.Stringify;
import Common.Commands.Command;
import Server.Storage.Collection.CollectionManager;

public class GetField extends Command {
    public GetField() {
        super("get_field_sorted",
                "Displays the field values of all elements in the given order",
                new CommandArgument[]{
                        new CommandArgument("field", String.class),
                        new CommandArgument("order", Integer.class)
                                .withValues(new Integer[]{0, 1})
        });
    }

    @Override
    public String execute(CollectionManager manager, Object[] args) {
        String fieldName = (String) args[0];
        Integer order = (Integer) args[1];

        return new Stringify(new Query(manager.getAll()).reduce(fieldName).sorted(order)).get();
    }
}