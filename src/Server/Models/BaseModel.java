package Server.Models;

import Common.Exceptions.InvalidModelException;
import Common.Tools;
import Server.Serializer.Serializer;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.Date;

public class BaseModel extends OrderedModel {
    public JSONObject json;

    public BaseModel() {
        this.json = new JSONObject();
    }

    private void set(Field f, Object value) throws IllegalAccessException {
        f.set(this, value);
    }

    public BaseModel from(JSONObject object) {
        Field[] fields = this.getClass().getFields();
        for (Field f : fields) {
            try {
                ModelField params = Serializer.getParameters(f);
                if (params == null) continue;

                f.setAccessible(true);
                Object value = object.get(f.getName());
                if (value == "") value = null;
                Class<?> type = f.getType();

                if (value == null && params.AUTO_GENERATE()) {
                    set(f, FieldGenerator.getValue(f));
                }

                if (Date.class.isAssignableFrom(type)) {
                    value = Tools.formatInputDate((String) value);
                    set(f, value);
                    continue;
                }

                if (value instanceof JSONObject) {
                    BaseModel m = (BaseModel) type.getDeclaredConstructor().newInstance();
                    m = m.from((JSONObject) value);
                    f.set(this, m);
                } else {
                    value = Serializer.serializeField(f, String.valueOf(value));
                    f.set(this, value);
                }
            } catch (Exception e) {
                throw new InvalidModelException(e.toString());
            }
        }
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


