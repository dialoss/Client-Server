package Common.Exceptions;

public class InvalidAmountOfArguments extends RuntimeException {
    public InvalidAmountOfArguments() {
        super("Недостаточно параметров для вызова команды");
    }
}
