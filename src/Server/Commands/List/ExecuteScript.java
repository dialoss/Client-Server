package Server.Commands.List;

import Client.CommandLineInterface;
import Client.Shell.Form;
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
        super("script", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        String filename = (String) args[0].getValue();
        String text = StorageConnector.storage.changeSource("scripts/" + filename)._read();
        CommandLineInterface virtual = new CommandLineInterface(new FileForm(new DevNull(new Scanner(text))));
        virtual.start();
        return "";
    }
}