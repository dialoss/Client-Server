package Server.Serializer.List;

import Common.Exceptions.InvalidModelException;

import java.lang.reflect.Field;

public class VNotEmpty extends Validator {

    public VNotEmpty() {
        super("NOT_EMPTY");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if (((String) value).length() == 0) throw new InvalidModelException("Empty string");
    }
}