package Server.Commands;

import Server.Models.Organization;
import Server.Storage.CollectionManager;

import java.util.stream.Stream;

public class Filter extends Command {
    public Filter() {
        super("filter_starts_with_name",
                "¬ыводит элементы, значение пол€ name которых начинаетс€ с заданной подстроки",
                new CommandArgument[]{new CommandArgument("name", "String")});
    }

    @Override
    public String execute(CollectionManager manager, String[] args) {
        String result = "";
//        for (Organization org : Stream.of(manager.getAll()).filter((Organization item) ->
//                item.fields.get("name").value.toString().startsWith("hello"))) {
//            result = result.concat(org.toString() + "\n");
//        }
        return result;
    }
}
