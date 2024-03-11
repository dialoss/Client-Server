package Common.Exceptions;

public class InvalidValue extends InvalidModelException {

    public InvalidValue(Object value, String field) {
        super(String.format("Значение %s не может быть присвоено полю %s", value, field));
    }
}
