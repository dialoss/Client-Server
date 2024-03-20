package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Stringify;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

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
    public String execute(CollectionManager manager, Map<String, Object> args) {
        String fieldName = (String) args.get("field");
        Integer order = (Integer) args.get("order");

        return new Stringify(new Query(manager.getAll()).reduce(fieldName).sorted(order)).get();
    }
}