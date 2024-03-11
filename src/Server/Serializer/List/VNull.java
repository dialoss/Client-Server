package Server.Serializer.List;

import Common.Exceptions.NotNullField;

import java.lang.reflect.Field;

public class VNull extends Validator {
    public VNull() {
        super("NULL");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) {
        if (!((boolean) declaredValue) &&
                (value == null || (value instanceof String && ((String)value).length() == 0)))
            throw new NotNullField(String.format("Поле %s не может быть NULL", f.getName()));
    }
}
