package Common.Exceptions;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException() {
        super("Empty input");
    }
}