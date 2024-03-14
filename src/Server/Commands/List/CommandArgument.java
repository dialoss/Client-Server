package Server.Commands.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandArgument implements Serializable {
    private final String name;
    public boolean required = true;
    public final Class<?> type;
    private Object value;
    public ArgumentPosition position = ArgumentPosition.LINE;
    public ArrayList<Object> possibleValues = new ArrayList<>();
    public Object defaultValue;

    public CommandArgument withPosition(ArgumentPosition position) {
        this.position = position;
        return this;
    }

    public CommandArgument withNotRequired(Object value) {
        this.required = false;
        this.defaultValue = value;
        return this;
    }

    public CommandArgument withValues(Object[] values) {
        this.possibleValues = new ArrayList<>(List.of(values));
        return this;
    }

    public CommandArgument(String name, Class<?> type) {
        this.name = name;
        this.type = type;
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
        return "%s - %s".formatted(this.name, this.type.getName()) +
                (possibleValues.size() != 0 ? " - Возможные значения: %s".formatted(possibleValues.toString()) : "");
    }
}