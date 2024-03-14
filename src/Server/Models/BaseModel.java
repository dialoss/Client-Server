package Server.Models;

import Common.Exceptions.InvalidModelException;
import Common.Exceptions.InvalidValue;
import Common.Tools;
import Server.Serializer.Serializer;
import Server.Storage.OrderedItem;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

class OrderedModel implements OrderedItem {
    @ModelField(MIN = 2, UNIQUE = true, AUTO_GENERATE = true)
    public Integer id;

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

    private void createField() {

    }

    public BaseModel from(JSONObject object) {
        Field[] fields = this.getClass().getFields();
        for (Field f : fields) {
            try {
                ModelField params = this.serializer.getParameters(f);
                if (params == null) continue;
                f.setAccessible(true);
                Object value = object.get(f.getName());
                if (value == "") value = null;
                Class<?> type = f.getType();

                if (value == null && params.AUTO_GENERATE()) {
                    try {
                        if (Integer.class.isAssignableFrom(type)) {
                            f.set(this, (int) (Math.random() * 1e9));
                        } else if (Number.class.isAssignableFrom(f.getType())) {
                            Object v = Serializer.castValue(type, String.valueOf(Math.random() * 1e9));
                            f.set(this, v);
                        } else {
                            f.set(this, type.getConstructor().newInstance());
                        }
                        continue;
                    } catch (Exception e) {
                        throw new InvalidModelException(e.toString());
                    }
                }

                if (Date.class.isAssignableFrom(type)) {
                    value = Tools.formatInputDate((String) value);
                    f.set(this, value);
                    continue;
                }

                if (value instanceof JSONObject) {
                    BaseModel m = (BaseModel) type.getDeclaredConstructor().newInstance();
                    m = m.from((JSONObject) value);
                    f.set(this, m);
                } else {
                    value = this.serializer.serializeField(f, String.valueOf(value));
                    f.set(this, value);
                }
            } catch (Exception e) {
                throw new InvalidModelException(e.toString());
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


