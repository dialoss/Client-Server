package Server.Commands;

public class ClientCommand {
    public final String name;
    public final String description;

    public ClientCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return this.name + ": " + this.description;
    }
}
