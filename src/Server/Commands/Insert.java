package Server.Commands;

import Server.Storage.CollectionManager;

public class Insert extends Command {
    public Insert() {
        super("insert", "��������� ����� ������� � �������� ������", new ItemArgument[]{new ItemArgument()});
    }

    @Override
    public String execute(CollectionManager collectionManager) {
        return null;
    }
}
