package exceptions;

public class InvalidStringLength extends RuntimeException {
    InvalidStringLength() {
        super("Длина строки больше 15");
    }
}
