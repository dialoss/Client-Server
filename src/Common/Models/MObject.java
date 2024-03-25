package Common.Models;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Describes base object with data in the app
 */
public class MObject extends HashMap<String, Object> implements Map<String, Object>, Serializable {
    public MObject() {
        super();
    }
    public MObject(Map<String, Object> map) {
        super(map);
    }

    public MObject(Object obj) throws IllegalAccessException {
        super();
        for (Field f : obj.getClass().getDeclaredFields()) {
            this.put(f.getName(), f.get(obj));
        }
    }
}
