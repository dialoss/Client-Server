package Common.Connection;

import java.io.Serializable;

public class Response implements Serializable {
    private final Object body;
    public final Status code;
    private UserClient userClient;

    public Response(Object body, Status code) {
        this.body = body;
        this.code = code;
    }

    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    public UserClient getClient() {
        return userClient;
    }

    public Object getBody() {
        return this.body;
    }
}