package Server.Models;

import Common.Tools;
import Server.Serializer.Serializer;
import Server.Storage.OrderedItem;
import Common.Exceptions.InvalidValue;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

class OrderedModel implements OrderedItem {
    @ModelField(MIN = 0, UNIQUE = true, AUTO_INCREMENT = true)
    private Integer id; //ѕоле не может быть null, «начение пол€ должно быть больше 0, «начение этого пол€ должно быть уникальным, «начение этого пол€ должно генерироватьс€ автоматически

    @Override
    public Integer getId() {
        return this.id;
    }
}

public class BaseModel extends OrderedModel {
    public JSONObject raw;
    Serializer serializer;

    public BaseModel() {
        this.serializer = new Serializer();
        this.raw = new JSONObject();
    }

    public BaseModel from(JSONObject object, boolean validate) {
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
                    m = m.from((JSONObject) value, true);
                    f.set(this, m);
                } else {
                    if (value instanceof Double && Float.class.isAssignableFrom(valueType)) {
                        value = ((Double) value).floatValue();
                    }
                    if (validate) this.serializer.validateField(f, value);
                    f.set(this, value);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        this.raw = object;
        return this;
    }

    public static boolean isModel(Class<?> type) {
        return BaseModel.class.isAssignableFrom(type);
    }

    @Override
    public String toString() {
        System.out.println(this.raw.toString());
        return Tools.beautifyJSON(this.raw.toString());
    }
}


