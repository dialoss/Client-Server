package Server.Commands.List;

import Common.EventBus.Callback;
import Common.Tools;
import Server.Commands.Command;
import Server.Models.BaseModel;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Filter extends Command {
    public Filter() {
        super("filter_starts_with_name",
                "¬ыводит элементы, значение пол€ name которых начинаетс€ с заданной подстроки",
                new CommandArgument[]{new CommandArgument("name", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        Object[] items = new Query(manager.getAll(), Organization.class)
                .filter("name", (Object value) -> ((String) value).startsWith("hello"))
                .filter("annualTurnover", (Object v) -> ((Float) v) < 50).get();

        return Tools.itemsToString(items);
    }
}
