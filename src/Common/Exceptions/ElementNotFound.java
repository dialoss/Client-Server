package Common.Exceptions;

public class ElementNotFound extends RuntimeException {
    public ElementNotFound(Integer id) {
        super("Ёлемент с id %s не найден".formatted(id));
    }
}
