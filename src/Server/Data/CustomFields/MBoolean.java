package Server.Data.CustomFields;

import Common.Exceptions.InvalidValue;

public class MBoolean extends CustomField {
    private final Boolean value;

    public MBoolean(Boolean value) {
        this.value = value;
    }

    public static MBoolean fromString(String s) {
        if (s.equals("true")) return new MBoolean(true);
        if (s.equals("false")) return new MBoolean(false);
        throw new InvalidValue(s, "boolean");
    }

    public static MBoolean valueOf(String s) {
        return fromString(s);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Boolean getValue() {
        return value;
    }

    public static Class<?> getType() {
        return Boolean.class;
    }
}