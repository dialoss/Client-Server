package Common.Exceptions;

public class ServerError extends RuntimeException {
    public ServerError(String message) {
        super(message);
    }
    public ServerError() {
        super("Server error");
    }

}