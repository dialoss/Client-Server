package exceptions;

public class NotNullField extends RuntimeException {
    public NotNullField() {
        super("Поле не может быть NULL");
    }
}
