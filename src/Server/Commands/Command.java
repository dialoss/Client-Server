package Server.Commands;

import Client.Shell.ShellColors;
import Server.Commands.List.CommandArgument;
import Server.Storage.CollectionManager;

interface IExecutable {
    String execute(CollectionManager collectionManager, CommandArgument[] arguments);
}

public abstract class Command implements IExecutable  {
    private final String name;
    public final String description;
    private final CommandArgument[] arguments;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.arguments = new CommandArgument[]{};
    }

    public Command(String name, String description, CommandArgument[] arguments) {
        this.name = name;
        this.description = description;
        this.arguments = arguments;
    }

    public String getDescription() {
        String description = ShellColors.BLUE + this.name + ShellColors.RESET;
        if (this.arguments.length > 0) {
            for (CommandArgument arg : this.arguments) {
                description = description.concat(" " + arg.getName());
            }
            description = description + ": " + this.description + " | Аргументы: ";
            for (CommandArgument arg : this.arguments) {
                description = description.concat(arg.toString() + ", ");
            }
        } else {
            description = description + ": " + this.description;
        }

        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }

    public CommandArgument[] getArguments() {
        return this.arguments;
    }

    public String getName() {
        return this.name;
    }

}
