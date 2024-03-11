package Server.Commands.List;

import Common.Tools;
import Server.Commands.Command;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Show extends Command {
    public Show() {
        super("show", "������� ��� �������� ��������� � ��������� �������������");
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        return Tools.itemsToString(manager.getAll());
    }
}
