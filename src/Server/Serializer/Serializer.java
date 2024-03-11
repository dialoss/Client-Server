package Server.Serializer;

import Server.Models.BaseModel;
import Server.Models.ModelField;
import exceptions.InvalidModelException;
import exceptions.InvalidValue;
import exceptions.NotNullField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Serializer {
    public Object serializeValue(Class<?> type, Object value) throws NoSuchMethodException {
        if (!value.getClass().isAssignableFrom(type)) {
            Method method = type.getDeclaredMethod("valueOf", String.class);
            try {
                return method.invoke(null, value);
            } catch (Exception e) {
                throw new InvalidValue(value, type.getName());
            }
        }
        return value;
    }

    public Object serializeField(Field f, Object value) throws Exception {
        Object typedValue = this.serializeValue(f.getType(), value);
        this.validateField(f, value);
        return typedValue;
    }

    public ModelField getParameters(Field f) {
        if (f.isAnnotationPresent(ModelField.class)) {
            return f.getAnnotation(ModelField.class);
        }
        return null;
    }

    public void validateField(Field f, Object value) throws Exception {
        try {
            ModelField params = this.getParameters(f);
            if (params == null) return;
            for (String param : Validators.getParams()) {
                if (Validators.get(param) != null) {
                    Validators.get(param).validate(
                            f,
                            value,
                            params.getClass().getDeclaredMethod(param).invoke(params));
                }
            }
        } catch (ClassCastException e) {
        } catch (Exception e) {
            throw e;
        }
    }
}