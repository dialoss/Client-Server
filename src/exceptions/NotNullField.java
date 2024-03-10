package exceptions;

public class NotNullField extends InvalidModelException {

    public NotNullField(String message) {
        super(message);
    }
}
