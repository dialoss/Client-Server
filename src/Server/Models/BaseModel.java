package Server.Models;

import Server.Serializer.Serializer;
import Server.Storage.OrderedItem;
import exceptions.InvalidModelException;
import exceptions.InvalidValue;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

class Model {
    Field[] getFields() {
        return this.getClass().getDeclaredFields();
    }
}

class OrderedModel extends Model implements OrderedItem {
    @ModelField(MIN = 0, UNIQUE = true, AUTO_INCREMENT = true)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Override
    public Integer getId() {
        return this.id;
    }
}

public class BaseModel extends OrderedModel {
    JSONObject raw;
    Serializer serializer;

    public BaseModel() {
        this.serializer = new Serializer(this);
    }

    public BaseModel from(JSONObject object) {
        this.raw = object;
        Class<? extends BaseModel> cl = this.getClass();
        for (Object key : object.keySet()) {
            try {
                Field f = cl.getDeclaredField(key.toString());
                f.setAccessible(true);
                Object value = object.get(key);
                Class<?> valueType = f.getType();
                boolean isInstance = BaseModel.class.isAssignableFrom(valueType);
                boolean isObject = value instanceof JSONObject;
                if (isInstance && !isObject) {
                    throw new InvalidValue(value, f.getName());
                }

                if (value instanceof JSONObject) {
                    BaseModel m = (BaseModel) valueType.getDeclaredConstructor().newInstance();
                    m = m.from((JSONObject) value);
                    f.set(this, m);
                } else {
                    if (value instanceof Double && Float.class.isAssignableFrom(valueType)) {
                        value = ((Double) value).floatValue();
                    }
                    this.serializer.validateField(f, value);
                    f.set(this, value);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        String result = "";
        for (Field f : this.getFields()) {
            try {
                f.setAccessible(true);
                Object value = f.get(this);
                result = result.concat(String.format("Поле: %s Значение: %s\n", f.getName(), value));
            } catch (Exception e) {
            }
        }
        return result;
    }
}


