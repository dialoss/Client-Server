package Server.Serializer.List;

import exceptions.NotNullField;

import java.lang.reflect.Field;

public class VNull extends Validator {
    public VNull() {
        super("NULL");
    }

    @Override
    public void validate(Field f, Object value, Object declaredValue) {
        if (!((boolean) declaredValue) && value == null)
            throw new NotNullField(String.format("Поле %s не может быть NULL", f.getName()));
    }
}
