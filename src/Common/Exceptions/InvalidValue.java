package Common.Exceptions;

public class InvalidValue extends InvalidModelException {

    public InvalidValue(Object value, String field) {
        super(String.format("Value %s cannot be assigned to field %s", value, field));
    }
    public InvalidValue(String message) {
        super(message);
    }
}