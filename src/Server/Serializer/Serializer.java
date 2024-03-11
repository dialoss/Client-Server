package Server.Serializer;

import Common.Tools;
import Server.Models.ModelField;
import Common.Exceptions.InvalidValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;


public class Serializer {
    public Object serializeValue(Class<?> type, Object value) {
        return this.serializeValue(type, value, type.getName());
    }

    public Object serializeValue(Field f, Object value) {
        Class<?> type = f.getType();
        return this.serializeValue(type, value, f.getName() + " " + type.getName());
    }

    private Object serializeValue(Class<?> type, Object value, String out) {
        if (!value.getClass().isAssignableFrom(type)) {
            try {
                if (Date.class.isAssignableFrom(type)) {
                    return Tools.serializeDate(value);
                }
                Method method = type.getDeclaredMethod("valueOf", String.class);
                return method.invoke(null, value);
            } catch (Exception e) {
                throw new InvalidValue(value, out);
            }
        }
        return value;
    }

    public Object serializeField(Field f, Object value) throws Exception {
        this.validateField(f, value);
        return this.serializeValue(f, value);
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
            if (params != null) {
                for (String param : Validators.getParams()) {
                    if (Validators.get(param) != null) {
                        Validators.get(param).validate(
                                f,
                                value,
                                params.getClass().getDeclaredMethod(param).invoke(params));
                    }
                }
            }
        } catch (ClassCastException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
            throw e;
        }
    }
}