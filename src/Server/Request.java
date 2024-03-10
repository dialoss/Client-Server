package Server;

public class Request {
    private final String[] arguments;
    private final String name;

    public Request(String name, String[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return this.name;
    }

    public String[] getArguments() {
        return this.arguments;
    }
}
