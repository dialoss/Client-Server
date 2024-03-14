package Common.Exceptions;

public class ElementNotFound extends RuntimeException {
    public ElementNotFound(Integer id) {
        super("Element with id %s not found".formatted(id));
    }
}