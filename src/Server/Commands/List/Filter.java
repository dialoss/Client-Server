package Server.Commands.List;

import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.UserClient;
import Common.Stringify;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Filter extends Command {
    public Filter() {
        super("filter_starts_with_name",
                "Displays elements whose name field value begins with the given substring",
                new CommandArgument[]{new CommandArgument("name", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, Map<String, Object> args, UserClient client) {
        String name = (String) args.get("name");
        Object[] items = new Query(manager.getAll())
                .filter("name", (Object value) -> ((String) value).startsWith(name)).get();

        return new Stringify(items).get();
    }
}