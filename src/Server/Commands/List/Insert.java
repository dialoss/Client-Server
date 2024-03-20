package Server.Commands.List;

import Common.Commands.ArgumentPosition;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Models.Organization;
import Server.Storage.Collection.CollectionManager;

import java.util.Map;

public class Insert extends Command {
    public Insert() {
        super("insert", "Adds a new element with the given key",
                new CommandArgument[]{
                        new CommandArgument("element", Organization.class)
                                .withPosition(ArgumentPosition.MULTILINE)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args) {
        collectionManager.insert((Organization) args.get("element"));
        return "Element added";
    }
}