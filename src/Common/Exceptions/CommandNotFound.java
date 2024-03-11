package Common.Exceptions;

public class CommandNotFound extends RuntimeException {
    public CommandNotFound() {
        super("Команда не найдена");
    }
}
