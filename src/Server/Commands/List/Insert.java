package Server.Commands.List;

import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Insert extends Command {
    public Insert() {
        super("insert", "Adds a new element with the given key",
                new CommandArgument[]{
                        new CommandArgument("element", Organization.class)
                                .withPosition(ArgumentPosition.MULTILINE)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        collectionManager.insert((Organization) args[0].getValue());
        return "Element added";
    }
}