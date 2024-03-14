package Server.Commands.List;

import Common.Stringify;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Filter extends Command {
    public Filter() {
        super("filter_starts_with_name",
                "¬ыводит элементы, значение пол€ name которых начинаетс€ с заданной подстроки",
                new CommandArgument[]{new CommandArgument("name", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        String name = (String) args[0].getValue();
        Object[] items = new Query(manager.getAll())
                .filter("name", (Object value) -> ((String) value).startsWith(name)).get();

        return new Stringify(items).get();
    }
}
