package Client.Exceptions;

public class ServerNotAvailableException extends Exception {
    public ServerNotAvailableException(String message) {
        super(message);
    }
    public ServerNotAvailableException() {
        super("Server is not available");
    }
}