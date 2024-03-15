package Server.Models;

import java.util.HashMap;
import java.util.Map;

public class MObject extends HashMap<String, Object> implements Map<String, Object> {
    public MObject() {
        super();
    }
    public MObject(Map<String, Object> map) {
        super(map);
    }
}
