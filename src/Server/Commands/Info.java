package Server.Commands;

public class Info extends Command implements IOutputCommand {
    public Info() {
        super("info", "������� ���������� � ��������� (���, ���� �������������, ���������� ��������� � �.�.)");
    }

    @Override
    public String execute() {
        return "HELLO";
    }
}
