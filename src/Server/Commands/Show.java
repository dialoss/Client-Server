package Server.Commands;

import Server.Request;

public class Show extends Command implements IRequestCommand {
    public Show() {
        super("show", "������� ��� �������� ��������� � ��������� �������������");
    }

    @Override
    public Request execute() {
        return null;
    }
}
