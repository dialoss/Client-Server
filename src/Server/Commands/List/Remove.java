package Server.Commands.List;

import Server.Commands.Command;
import Server.Commands.ServerCommand;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Remove extends ServerCommand {
    public Remove() {
        super("remove", "������� ������� �� ��������� �� ��� �����",
                new CommandArgument[]{new CommandArgument("id", Integer.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        Integer id = (Integer) args[0].getValue();
        collectionManager.delete(id);
        return String.format("������� %s �����", id);
    }
}
