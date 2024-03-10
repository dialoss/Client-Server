package Server.Commands.List;

public class CommandArgument {
    private final String name;
    private final String valueType;

    CommandArgument(String name, String valueType) {
        this.name = name;
        this.valueType = valueType;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.valueType;
    }
}

class ItemArgument extends CommandArgument {
    ItemArgument() {
        super("element", "Organization");
    }
}
