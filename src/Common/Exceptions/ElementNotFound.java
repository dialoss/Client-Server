package Common.Exceptions;

public class ElementNotFound extends RuntimeException {
    public ElementNotFound(Integer id) {
        super("������� � id %s �� ������".formatted(id));
    }
}
