package Server.Commands.List;

import Common.Stringify;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

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
    public String execute(CollectionManager manager, CommandArgument[] args) {
        String fieldName = (String) args[0].getValue();
        Integer order = (Integer) args[1].getValue();

        return new Stringify(new Query(manager.getAll()).reduce(fieldName).sorted(order)).get();
    }
}