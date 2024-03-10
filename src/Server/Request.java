package Server;

public class Request {
    private final String body;
    private final String name;

    public Request(String name) {
        this.name = name;
        this.body = null;
    }

    public String getName() {
        return this.name;
    }
}
