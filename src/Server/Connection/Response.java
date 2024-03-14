package Server.Connection;

import java.io.Serializable;

public class Response implements Serializable {
    private final String body;
    public final Status code;
    private Integer client;

    public Response(Object body, Status code) {
        this.body = (String) body;
        this.code = code;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getClient() {
        return client;
    }

    public String getBody() {
        return this.body;
    }
}
