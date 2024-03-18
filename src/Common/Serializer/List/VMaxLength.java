package Common.Serializer.List;

import Common.Exceptions.InvalidModelException;

import java.lang.reflect.Field;

public class VMaxLength extends Validator {
    public VMaxLength() {
        super("MAX_LENGTH");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if (((String) value).length() >= (Integer) declaredValue)
            throw new InvalidModelException("Maximum string length "+ declaredValue);
    }
}