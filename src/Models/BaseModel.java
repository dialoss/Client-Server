package Models;

import javax.security.auth.callback.Callback;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

interface IValidated {
    Exception validate();
}

abstract class FieldParameter<T> implements IValidated {
    private String name;
    private T value;
    FieldParameter(String name, T value) {
        this.name = name;
        this.value = value;
    }
    @Override
    public Exception validate() {
        return null;
    }
}

enum FieldParameters {
    NOT_NULL,
    NOT_EMPTY,
    MIN,
    MAX,
    UNIQUE,
    AUTO_GENERATE,
    MIN_LENGTH,
    MAX_LENGTH,
}

class ModelField<T> {
    Map<FieldParameters, Object> parameters;

    ModelField(Map<FieldParameters, Object> parameters) {
        this.parameters = parameters;
    }
}

public class BaseModel {

}
