package Client.Commands;

public class Info extends Command implements IOutputCommand {
    @Override
    public String execute() {
        return "HELLO";
    }

    @Override
    public String description() {
        return "������� ���������� � ��������� (���, ���� �������������, ���������� ��������� � �.�.)";
    }
}
