package exceptions;

public class NotNullField extends RuntimeException {
    NotNullField() {
        super("Поле не может быть NULL");
    }
}
