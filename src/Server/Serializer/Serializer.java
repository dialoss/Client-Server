package Server.Serializer;

import Server.Models.BaseModel;
import Server.Models.ModelField;
import exceptions.InvalidModelException;
import exceptions.InvalidValue;
import exceptions.NotNullField;

import java.lang.reflect.Field;
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
//        if (value <= declaredValue) throw new InvalidValue("Значение поля должно быть больше 0");
    }
}


// NULL,
//         NOT_EMPTY,
//         MIN,
//         MAX,
//         UNIQUE,
//         AUTO_GENERATE,
//         MIN_LENGTH,
//         MAX_LENGTH;

class Validators {
    static HashMap<String, Validator> validators = new HashMap<>();

    static {
        Validators.add(new VNull());
        Validators.add(new VMin());
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

    BaseModel model;

    public Serializer(BaseModel model) {
        this.model = model;
    }

    public boolean validate() {
        for (Field f : this.model.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                Object value = f.get(this.model);
                if (f.isAnnotationPresent(ModelField.class)) {
                    ModelField params = f.getAnnotation(ModelField.class);
                    for (String param : Validators.getParams()) {
                        if (Validators.get(param) != null) {
                            Validators.get(param).validate(
                                    f,
                                    value,
                                    params.getClass().getDeclaredMethod(param).invoke(params));
                        }
                    }
                    if (params.AUTO_GENERATE()) {
                        f.set(this.model, f.getType().getDeclaredConstructor().newInstance());
                    }
                }
            } catch (InvalidModelException e) {
                throw e;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return true;
    }
}


