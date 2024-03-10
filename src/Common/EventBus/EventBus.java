package Common.EventBus;

import java.util.HashMap;
import java.util.Map;

public class EventBus {
    private static HashMap<String, Callback> callbacks;

    public static void on(String event, Callback callback) {
        EventBus.callbacks.put(event, callback);
    }

    public static void emit(String event, Object data) {
        EventBus.callbacks.get(event).call(data);
    }
}
