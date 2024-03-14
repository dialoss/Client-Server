package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Exit extends Command {
    public Exit() {
        super("exit", "��������� ��������� (��� ���������� � ����)");
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        System.exit(0);
        return "";
    }
}
