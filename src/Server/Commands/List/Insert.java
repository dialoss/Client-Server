package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class Insert extends Command {
    public Insert() {
        super("insert", "��������� ����� ������� � �������� ������", new ItemArgument[]{new ItemArgument()});
    }

    @Override
    public String execute(CollectionManager collectionManager, String[] args) {
        return null;
    }
}
