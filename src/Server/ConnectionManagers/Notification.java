package Server.ConnectionManagers;

import java.util.HashMap;

public class Notification {
    static HashMap<Integer, Boolean> ports = new HashMap<>();

    public synchronized static void set() {
        ports.replaceAll((p, v) -> true);
    }

    public static synchronized Boolean get(Integer port) {
        Boolean res = false;
        if (ports.containsKey(port)) res = ports.get(port);
        ports.put(port, false);
        return res;
    }
}
