package Server.Commands.List;

import Client.CommandLineInterface;
import Client.CommandParser;
import Client.Shell.Form;
import Client.Shell.IOdevice;
import Server.Commands.Command;
import Server.Commands.List.CommandArgument;
import Server.Internal.DevNull;
import Server.Internal.FileForm;
import Server.Models.Organization;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Scanner;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("script", "������� � ��������� ������ �� ���������� �����. � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String filename = (String) args[0].getValue();
        String text = StorageConnector.storage.changeSource("scripts/" + filename)._read();
        IOdevice virtual = new DevNull(new Scanner(text));
        CommandParser parser = new CommandParser(new FileForm(virtual));
        virtual.start(parser::parse);
        return "";
    }
}