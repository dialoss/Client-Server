package Common.Exceptions;

public class NotNullField extends InvalidModelException {

    public NotNullField(String message) {
        super(message);
    }
}