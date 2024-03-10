package Server.Commands;

import Server.Storage.CollectionManager;

public class Info extends Command {
    public Info() {
        super("info", "������� ���������� � ��������� (���, ���� �������������, ���������� ��������� � �.�.)");
    }

    @Override
    public String execute(CollectionManager collectionManager, String[] args) {
        return collectionManager.info.getInfo();
    }
}
