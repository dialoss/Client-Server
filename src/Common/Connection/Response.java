package Common.Connection;

import java.io.Serializable;

public class Response implements Serializable {
    private final String body;
    public final Status code;
    private final UserClient userClient;

    public Response(Object body, Status code, UserClient userClient) {
        this.body = (String) body;
        this.code = code;
        this.userClient = userClient;
    }

    public UserClient getClient() {
        return userClient;
    }

    public String getBody() {
        return this.body;
    }
}