package Server.Commands.List;

import Client.CommandParser;
import Client.Shell.IForm;
import Client.Shell.IOdevice;
import Common.Exceptions.ScriptRuntimeException;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Internal.DevNull;
import Server.Internal.FileForm;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Scanner;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("script", "������� � ��������� ������ �� ���������� �����. � ������� ���������� ������� � ����� �� ����, � ������� �� ������ ������������ � ������������� ������.",
                new CommandArgument[]{new CommandArgument("filename", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) throws ScriptRuntimeException {
        String filename = (String) args[0].getValue();
        try {
            String text = StorageConnector.storage.changeSource("scripts/" + filename)._read();
            IOdevice virtual = new DevNull(new Scanner(text));
            IForm form = new FileForm(virtual);
            CommandParser parser = new CommandParser(form);
            virtual.start((String[] command) -> form.out(CommandManager.execute(parser.parse(command)).getBody()));
        } catch (Exception e) {
            throw new ScriptRuntimeException(e.toString());
        }
        return "������ %s ��������".formatted(filename);
    }
}