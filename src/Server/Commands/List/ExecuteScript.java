package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("script", "������� � ��������� ������ �� ���������� �����. � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return "privet";
    }
}
