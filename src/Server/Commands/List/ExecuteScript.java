package Server.Commands.List;

import Server.CommandManager;
import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("execute_script", "������� � ��������� ������ �� ���������� �����. � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String info = "";
        for (Command command : CommandManager.get().values()) {
            info = info.concat(command.getDescription() + "\n");
        }
        return info;
    }
}
