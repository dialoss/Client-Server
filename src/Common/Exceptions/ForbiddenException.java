package Common.Exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You have no access to this resource");
    }
}