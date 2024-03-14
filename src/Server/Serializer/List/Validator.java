package Server.Serializer.List;

import Common.Exceptions.InvalidModelException;

import java.lang.reflect.Field;

public abstract class Validator {
    public String name;

    public Validator(String name) {
        this.name = name;
    }

    public abstract void validate(Field f, Object value, Object declaredValue) throws InvalidModelException;
}