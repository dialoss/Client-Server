package Server;

public class Response {
    private final String body;
    public final Status code;

    public Response(Object body, Status code) {
        this.body = (String) body;
        this.code = code;
    }

    public String getBody() {
        return this.body;
    }
}
