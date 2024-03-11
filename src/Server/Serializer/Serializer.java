package Server.Serializer;

import Server.Models.BaseModel;
import Server.Models.ModelField;
import exceptions.InvalidModelException;
import exceptions.InvalidValue;
import exceptions.NotNullField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

abstract class Validator {
    String name;

    Validator(String name) {
        this.name = name;
    }

    abstract void validate(Field f, Object value, Object declaredValue) throws InvalidModelException;
}

class VNull extends Validator {
    VNull() {
        super("NULL");
    }

    @Override
    void validate(Field f, Object value, Object declaredValue) {
        if (!((boolean) declaredValue) && value == null)
            throw new NotNullField(String.format("Поле %s не может быть NULL", f.getName()));
    }
}

class VMin extends Validator {
    VMin() {
        super("MIN");
    }

    @Override
    void validate(Field f, Object value, Object declaredValue) {
        if (!Double.class.isAssignableFrom(value.getClass())) return;
        if ((Double) value <= (Double) declaredValue)
            throw new InvalidModelException("Значение поля должно быть больше 0");
    }
}

class VMaxLength extends Validator {
    VMaxLength() {
        super("MAX_LENGTH");
    }

    @Override
    void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if (((String) value).length() >= (Integer) declaredValue)
            throw new InvalidModelException("Слишком длинный поле");
    }
}

class VMinLength extends Validator {
    VMinLength() {
        super("MIN_LENGTH");
    }

    @Override
    void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if (((String) value).length() <= (Integer) declaredValue)
            throw new InvalidModelException("Слишком короткая поле");
    }
}

class VNotEmpty extends Validator {

    VNotEmpty() {
        super("NOT_EMPTY");
    }

    @Override
    void validate(Field f, Object value, Object declaredValue) throws InvalidModelException {
        if (((String) value).length() == 0) throw new InvalidModelException("Пустая строка");
    }
}

class Validators {
    static HashMap<String, Validator> validators = new HashMap<>();

    static {
        Validators.add(new VNull());
        Validators.add(new VMin());
        Validators.add(new VMaxLength());
        Validators.add(new VMinLength());
        Validators.add(new VNotEmpty());

    }

    static Validator get(String name) {
        return Validators.validators.get(name);
    }

    static void add(Validator v) {
        Validators.validators.put(v.name, v);
    }

    static String[] getParams() {
        return Validators.validators.keySet().toArray(String[]::new);
    }
}

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