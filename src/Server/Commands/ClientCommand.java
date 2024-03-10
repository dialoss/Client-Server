package Server.Commands;

public class ClientCommand {
    private final String name;
    private final String description;
    private final CommandArgument[] arguments;

    public ClientCommand(String name, String description) {
        this.name = name;
        this.description = description;
        this.arguments = new CommandArgument[]{};
    }

    public ClientCommand(String name, String description, CommandArgument[] arguments) {
        this.name = name;
        this.description = description;
        this.arguments = arguments;
    }

    public String getDescription() {
        String description = this.name;
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

    public String getName() {
        return this.name;
    }
}
