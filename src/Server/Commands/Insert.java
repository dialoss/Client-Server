package Server.Commands;

public class Insert extends Command implements IOutputCommand {
    public Insert() {
        super("insert", "��������� ����� ������� � �������� ������", new ItemArgument[]{new ItemArgument()});
    }

    @Override
    public String execute() {
        return "HELLO";
    }
}
