package Server.Commands;

public class Insert extends Command implements IOutputCommand {
    public Insert() {
        super("insert", "��������� ����� ������� � �������� ������");
    }

    @Override
    public String execute() {
        return "HELLO";
    }
}
