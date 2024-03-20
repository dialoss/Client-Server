package Client.Exceptions;

public class RequestError extends Exception {
    public RequestError(String message) {
        super(message);
    }
    public RequestError(Exception e) {
        super(e);
    }
}