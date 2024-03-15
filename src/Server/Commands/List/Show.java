package Server.Commands.List;

import Common.Stringify;
import Server.Commands.Command;
import Server.Storage.Collection.CollectionManager;

import java.util.Arrays;

public class Show extends Command {
    public Show() {
        super("show", "Displays all elements of a collection in string representation",
                new CommandArgument[]{
                        new CommandArgument("limit", Integer.class).withNotRequired((int)1e9)
                });
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        Integer limit = (Integer) args[0].getValue();
        Object[] all = manager.getAll();
        return new Stringify(Arrays.copyOfRange(all, 0, Math.min(limit, all.length))).withSplitLines().get();
    }
}