package Server.Commands.List;

import Client.CommandLineInterface;
import Client.Shell.Form;
import Server.Commands.Command;
import Server.Commands.List.CommandArgument;
import Server.Internal.DevNull;
import Server.Internal.FileForm;
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
        Form f = new Form(null,
                new FileForm(new DevNull(new Scanner(StorageConnector.storage.changeSource("scripts/test")._read()))));
        f.get();
        return "Скрипт запущен";
    }
}