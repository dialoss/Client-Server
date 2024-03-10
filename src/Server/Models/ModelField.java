package Server.Models;

import java.util.Map;

public class ModelField<T> {
    Map<FieldParameters, Object> parameters;
    public T value;

    ModelField(T defaultValue, Map<FieldParameters, Object> parameters) {
        this.parameters = parameters;
        this.value = defaultValue;
    }

    public boolean validate(Object value) {
        for (FieldParameters param : this.parameters.keySet()) {
//            if (!param.validate(value)) return false;
        }
        return true;
    }
}
