package Common.Serializer.List;

import Common.Exceptions.InvalidModelException;

import java.lang.reflect.Field;

public class VMin extends Validator {
    public VMin() {
        super("MIN");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) {
        if (!Double.class.isAssignableFrom(value.getClass())) return;
        if ((Double) value <= (Double) declaredValue)
            throw new InvalidModelException("The field value must be greater than 0");
    }
}