package Server;

public class Response {
    private final String body;

    public Response(Object body) {
        this.body = (String) body;
    }

    public String getBody() {
        return this.body;
    }
}
