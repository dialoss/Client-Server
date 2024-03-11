package Server.Commands.List;

import Common.Tools;
import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

import java.util.stream.Stream;

public class Filter extends Command {
    public Filter() {
        super("filter_starts_with_name",
                "¬ыводит элементы, значение пол€ name которых начинаетс€ с заданной подстроки",
                new CommandArgument[]{new CommandArgument("name", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        return Tools.itemsToString(
                Stream.of(manager.getAll()).filter((Organization item) ->
                        item.name.startsWith("hello")).toArray());
    }
}
