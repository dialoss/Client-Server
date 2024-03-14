package Common.Exceptions;

public class InvalidModelException extends RuntimeException {
    public InvalidModelException(String message) {
        super(message);
    }
}