package Server.Commands.List;

import Common.Tools;
import Server.Commands.ServerCommand;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Ascending extends ServerCommand {
    public Ascending() {
        super("print_field_ascending",
                "Выводит значения поля field всех элементов в порядке возрастания",
                new CommandArgument[]{new CommandArgument("field", String.class)});
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        String fieldName = (String) args[0].getValue();
        Organization[] items = manager.getAll();
        List<Object> values = new ArrayList<>();
        for (Organization it : items) {
            Field field = (Field) Arrays.stream(it.getClass().getDeclaredFields())
                    .filter((Field f) -> f.getName() == fieldName).toArray()[0];
            try {
                values.add(field.get(it));
            } catch (Exception e) {}
        }
        return Tools.itemsToString(Stream.of(values).sorted().toArray());
    }
}
