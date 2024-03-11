package Server.Commands.List;

import Server.Commands.Command;
import Server.Commands.ServerCommand;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Update extends ServerCommand {
    public Update() {
        super("update", "��������� �������� �������� ���������, id �������� ����� ���������",
                new CommandArgument[]{
                        new CommandArgument("id", Integer.class, ArgumentPosition.LINE),
                        new CommandArgument("element", Organization.class, ArgumentPosition.COMPLEX)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        Organization item = (Organization) args[1].getValue();
        Integer id = (Integer) args[0].getValue();
        collectionManager.update(id, item);
        return "������� �������";
    }
}
