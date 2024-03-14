package Server.Commands.List;

import Client.CommandParser;
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
        super("script", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
                new CommandArgument[]{new CommandArgument("filename", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) throws ScriptRuntimeException {
        String filename = (String) args[0].getValue();
        try {
            String text = StorageConnector.storage.changeSource("scripts/" + filename)._read();
            IOdevice virtual = new DevNull(new Scanner(text));
            CommandParser parser = new CommandParser(new FileForm(virtual));
            virtual.start((String[] command) -> CommandManager.execute(parser.parse(command)));
        } catch (Exception e) {
            throw new ScriptRuntimeException(e.toString());
        }
        return "";
    }
}