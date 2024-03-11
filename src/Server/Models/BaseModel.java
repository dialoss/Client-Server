package Server.Models;

import Common.Tools;
import Server.Serializer.Serializer;
import Server.Storage.OrderedItem;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

class OrderedModel implements OrderedItem {
    @ModelField(MIN = 0, UNIQUE = true, AUTO_INCREMENT = true)
    public Integer id; //ѕоле не может быть null, «начение пол€ должно быть больше 0, «начение этого пол€ должно быть уникальным, «начение этого пол€ должно генерироватьс€ автоматически

    @Override
    public Integer getId() {
        return this.id;
    }
}

public class BaseModel extends OrderedModel {
    public JSONObject json;
    Serializer serializer;

    public BaseModel() {
        this.serializer = new Serializer();
        this.json = new JSONObject();
    }

    public BaseModel from(JSONObject object) {
        Class<? extends BaseModel> cl = this.getClass();
        for (Object key : object.keySet()) {
            try {
                Field f = cl.getField(key.toString());
                f.setAccessible(true);
                Object value = object.get(key);
                Class<?> valueType = f.getType();

                if (value instanceof JSONObject) {
                    BaseModel m = (BaseModel) valueType.getDeclaredConstructor().newInstance();
                    m = m.from((JSONObject) value);
                    f.set(this, m);
                } else {
                    value = this.serializer.serializeField(f, value.toString());
                    f.set(this, value);
                }
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
        this.json = object;
        return this;
    }

    public static boolean isModel(Class<?> type) {
        return BaseModel.class.isAssignableFrom(type);
    }

    @Override
    public String toString() {
        return Tools.beautifyJSON(this.json.toString());
    }
}


