package Server.Commands.List;

import Server.Commands.Command;
import Server.Storage.CollectionManager;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("script", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) {
        return "privet";
    }
}
