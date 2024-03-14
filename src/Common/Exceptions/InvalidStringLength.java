package Common.Exceptions;

public class InvalidStringLength extends RuntimeException {
    InvalidStringLength() {
        super("String length is greater than 15");
    }
}