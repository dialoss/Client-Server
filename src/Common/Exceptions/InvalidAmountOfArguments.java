package Common.Exceptions;

public class InvalidAmountOfArguments extends RuntimeException {
    public InvalidAmountOfArguments(String message) {
        super("������������ ���������� ��� ������ �������. " + message);
    }
}
