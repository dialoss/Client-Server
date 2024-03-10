package exceptions;

public class InvalidModelException extends RuntimeException {
    InvalidModelException(String message) {
        super(message);
    }
}
