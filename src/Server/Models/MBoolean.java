package Server.Models;

import Common.Exceptions.InvalidValue;

public class MBoolean {
    private final Boolean value;

    public MBoolean(Boolean value) {
        this.value = value;
    }

    public static MBoolean valueOf(String s) {
        if (s.equals("true")) return new MBoolean(true);
        if (s.equals("false")) return new MBoolean(false);
        throw new InvalidValue(s, "boolean");
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Boolean getValue() {
        return value;
    }
}