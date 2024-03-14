package Common.Exceptions;

public class InvalidAmountOfArguments extends RuntimeException {
    public InvalidAmountOfArguments(String message) {
        super("There are not enough parameters to call the command." + message);
    }
}