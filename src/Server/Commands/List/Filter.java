package Server.Commands.List;

import Common.Tools;
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
        Object[] items = new Query(manager.getAll())
                .filter("name", (Object value) -> ((String) value).startsWith("hello"))
                .filter("annualTurnover", (Object v) -> ((Float) v) < 50).get();

        return Tools.stringify(items);
    }
}
