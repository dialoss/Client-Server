package Server.Commands;

public abstract class Command extends ClientCommand implements IExecutable {
    protected Command(String name, String description) {
        super(name, description);
    }
}

