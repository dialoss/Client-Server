package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.UserClient;
import Common.Stringify;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Ascending extends Command {
    public Ascending() {
        super("print_field_ascending",
                "Displays the field values of all elements in ascending order",
                new CommandArgument[]{new CommandArgument("field", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, Map<String, Object> args, UserClient client) {
        String fieldName = (String) args.get("field");

        return new Stringify(new Query(manager.getAll()).reduce(fieldName).sorted()).get();
    }
}