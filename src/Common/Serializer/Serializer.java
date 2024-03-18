package Common.Serializer;

import Common.Exceptions.InvalidValue;
import Common.Models.ModelField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Serializer {
    public static Object castValue(Class<?> type, Object value) throws Exception {
        Method method = type.getDeclaredMethod("valueOf", String.class);
        return method.invoke(null, value);
    }

    public static Object serializeValue(Class<?> type, Object value) {
        return serializeValue(type, value, type.getName());
    }

    public static Object serializeValue(Field f, Object value) {
        Class<?> type = f.getType();
        return serializeValue(type, value, f.getName() + " " + type.getName());
    }

    private static Object serializeValue(Class<?> type, Object value, String out) {
        if (!value.getClass().isAssignableFrom(type)) {
            try {
                return castValue(type, value);
            } catch (Exception e) {
                throw new InvalidValue(value, out);
            }
        }
        return value;
    }

    public static Object serializeField(Field f, Object value) throws Exception {
        validateField(f, value);
        return serializeValue(f, value);
    }

    public static ModelField getParameters(Field f) {
        if (f.isAnnotationPresent(ModelField.class)) {
            return f.getAnnotation(ModelField.class);
        }
        return null;
    }

    public static void validateField(Field f, Object value) throws Exception {
        ModelField params = getParameters(f);
        for (String param : Validators.getParams()) {
            if (Validators.get(param) != null) {
                Validators.get(param).validate(
                        f,
                        value,
                        params.getClass().getDeclaredMethod(param).invoke(params));
            }
        }
    }
}