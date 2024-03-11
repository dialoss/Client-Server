package Client.Commands;

import Client.CommandLineInterface;
import Common.Tools;
import Server.Commands.CommandManager;
import Server.Commands.List.CommandArgument;

public class Help extends ClientCommand {
    public Help() {
        super("help", "������� ������� �� ��������� ��������");
    }


    @Override
    public String execute(CommandLineInterface cli, CommandArgument[] args) {
        return Tools.itemsToString(CommandManager.getAll().values().toArray());
    }
}
