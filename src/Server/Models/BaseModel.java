package Server.Models;

import Server.Storage.OrderedItem;
import exceptions.NotNullField;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static Server.Models.FieldParameters.*;
import static Server.Models.FieldParameters.AUTO_GENERATE;

@FunctionalInterface
interface IValidated {
    boolean validate(Object value, Object limit);
}

enum FieldParameters {
    NOT_NULL((value, limit) -> !(value == null)),
    NOT_EMPTY((value, limit) -> ((String) value).length() > 0),
    MIN((value, limit) -> ((Number) value).doubleValue() > ((Number) limit).doubleValue()),
    MAX((value, limit) -> ((Number) value).doubleValue() < ((Number) limit).doubleValue()),
    UNIQUE((value, limit)  -> ((Number) value).doubleValue() > ((Number) limit).doubleValue()),
    AUTO_GENERATE((value, limit)  -> ((Number) value).doubleValue() > ((Number) limit).doubleValue());
//    MIN_LENGTH,
//    MAX_LENGTH;

    final IValidated validator;

    FieldParameters(IValidated validator) {
        this.validator = validator;
    }
}

class ModelField<T> {
    Map<FieldParameters, Object> parameters;
    T value;

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

class OrderedModel implements OrderedItem {
    private final ModelField<Long> id = new ModelField<>(0L, Map.of(
            NOT_NULL, true,
            MIN, 0,
            UNIQUE, true,
            AUTO_GENERATE, true
    ));

    @Override
    public Integer getId() {
        return Math.toIntExact(this.id.value);
    }
}

public class BaseModel extends OrderedModel {

    JSONObject raw;
    public BaseModel from(JSONObject object) {
        this.raw = object;
        return this;
    }

    public boolean validate() {
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Object key : this.raw.keySet()) {
            try {
                allFields[0].setAccessible(true);
                Object value = this.raw.get(key);
                ModelField<Object> val = (ModelField<Object>) allFields[0].get(this);
                if (!val.validate(value)) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
