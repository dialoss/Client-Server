package Server.Models;

import Server.Storage.OrderedItem;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
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

class OrderedModel extends OrderedItem {
    private final ModelField<Long> id = new ModelField<>((long) Math.floor(Math.random() * 10000), Map.of(
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

    public Map<String, ModelField<?>> fields;
    JSONObject raw;

    public BaseModel() {
        this.fields = new HashMap<>();
    }
    public BaseModel from(JSONObject object) {
        this.raw = object;
        return this;
    }

    public boolean validate() {
        for (Object key : this.raw.keySet()) {
//            try {
////                allFields[0].setAccessible(true);
////                Object value = this.raw.get(key);
////                ModelField<Object> val = (ModelField<Object>) allFields[0].get(this);
////                if (!val.validate(value)) {
////                    return false;
//                }
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
        }
        return true;
    }

    @Override
    public String toString() {
//        Field[] allFields = this.fields;
        String result = "";

        for (String key : this.fields.keySet()) {
            try {
//                f.setAccessible(true);
//                ModelField<Object> val = (ModelField<Object>) allFields[0].get(this);
                result = result.concat(String.format("Поле: %s Значение: %s\n", key, this.fields.get(key).value));
            } catch (Exception e) {

            }
        }
        return result;
    }
}
