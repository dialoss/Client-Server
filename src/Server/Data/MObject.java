package Server.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MObject extends HashMap<String, Object> implements Map<String, Object> {
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
