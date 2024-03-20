package Common;

import Common.Connection.ICallback;
import Common.Connection.Response;

import java.util.HashMap;

public class EventBus {
    public static final HashMap<String, ICallback<Object>> callbacks = new HashMap<>();

    public static void on(String event, ICallback<Object> callback) {
        EventBus.callbacks.put(event, callback);
    }

    public static Response emit(String event, Object data) throws Exception {
        return EventBus.callbacks.get(event).call(data);
    }
}