package Server.Commands.List;

public class CommandArgument {
    private final String name;
    private final boolean required;
    public final Class<?> type;
    private Object value;
    public ArgumentPosition position = ArgumentPosition.LINE;

    CommandArgument(String name, Class<?> type, ArgumentPosition position) {
        this.name = name;
        this.type = type;
        this.required = true;
        this.position = position;
    }

    public CommandArgument(String name, Class<?> type) {
        this.name = name;
        this.type = type;
        this.required = true;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.type.getName();
    }
}