package Common.Connection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConnectionPackage implements Serializable {
    protected final Map<String, String> headers = new HashMap<>();
    protected UserClient userClient;

    public String getHeader(String header) {
        return headers.get(header);
    }

    public String setHeader(String header, String value) {
        return headers.put(header, value);
    }

    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    public UserClient getClient() {
        return userClient;
    }
}
