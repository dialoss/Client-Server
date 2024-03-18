package Server.Commands.List;

import Common.Commands.ArgumentPosition;
import Common.Commands.CommandArgument;
import Common.Stringify;
import Common.Commands.Command;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RemoveGreater extends Command {
    public RemoveGreater() {
        super("remove_greater", "Removes from the collection all elements greater than the given one",
                new CommandArgument[]{
                        new CommandArgument("element", Organization.class)
                                .withPosition(ArgumentPosition.MULTILINE)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, Object[] args) {
        Organization organization = (Organization) args[0];
        ArrayList<Integer> deleted = new ArrayList<>();
        Stream.of(collectionManager.getAll())
            .filter((Object it) -> (0 < ((Organization) it).compareTo(organization)))
            .forEach(it -> {
                Integer id = it.getId();
                collectionManager.delete(id);
                deleted.add(id);
            });

        return "Items deleted:\n%s".formatted(new Stringify(deleted.toArray()).get());
    }
}