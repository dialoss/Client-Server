package Server.Serializer.List;

import exceptions.InvalidModelException;

import java.lang.reflect.Field;

public class VMinLength extends Validator {
    public VMinLength() {
        super("MIN_LENGTH");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if (((String) value).length() <= (Integer) declaredValue)
            throw new InvalidModelException("Слишком короткая поле");
    }
}
