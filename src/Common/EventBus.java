package Common;

import javax.security.auth.callback.Callback;
import java.util.HashMap;

public class EventBus {
    static HashMap<String, Callback> callbacks;

    static void on(String name, Callback callback) {
        EventBus.callbacks.put(name, callback);
    }

    static void emit(String name) {
        EventBus.callbacks.get(name);
    }
}
