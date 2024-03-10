package Server.Models;

import Server.Storage.OrderedItem;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static Server.Models.FieldParameters.*;

class OrderedModel extends OrderedItem {
    public Map<String, ModelField<?>> fields;

    public OrderedModel() {
        this.fields = new HashMap<>();
        this.fields.put("id", new ObjectBuilder<>(new ModelField<>(0L))
                .set(NULL, true)
                .set(MIN, 0)
                .set(UNIQUE, true)
                .set(AUTO_GENERATE, true).get());
    }

    @Override
    public Integer getId() {
        return Math.toIntExact((long) this.fields.get("id").value);
    }
}

public class BaseModel extends OrderedModel implements IObjectBuilder {

    JSONObject raw;

    public BaseModel() {
        super();
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

    @Override
    public void addParameter(Object parameter, Object value) {
        this.fields.put((String) parameter, (ModelField<?>) value);
    }
}


