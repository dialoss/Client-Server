package exceptions;

public class InvalidValue extends RuntimeException {
    InvalidValue() {
        super("Значение поля должно быть больше 0");
    }
}
