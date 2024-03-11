package Client.Commands;

import Client.CommandLineInterface;
import Server.Commands.List.CommandArgument;

public class ExecuteScript extends ClientCommand {
    public ExecuteScript() {
        super("script", "������� � ��������� ������ �� ���������� �����. � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CommandLineInterface cli, CommandArgument[] args) {
        cli.shell.setScript((String) args[0].getValue());
        return "������ �������";
    }
}