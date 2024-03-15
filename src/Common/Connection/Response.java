package Common.Connection;

import java.io.Serializable;

public class Response implements Serializable {
    private final String body;
    public final Status code;
    private final User client;

    public Response(Object body, Status code, User client) {
        this.body = (String) body;
        this.code = code;
        this.client = client;
    }

    public Integer getClient() {
        return client.sessionId;
    }

    public String getBody() {
        return this.body;
    }
}