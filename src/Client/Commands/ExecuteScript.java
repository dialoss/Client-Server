package Client.Commands;

import Client.CommandLineInterface;
import Server.Commands.List.CommandArgument;

public class ExecuteScript extends ClientCommand {
    public ExecuteScript() {
        super("script", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.",
                new CommandArgument[]{new CommandArgument("file_name", String.class)});
    }

    @Override
    public String execute(CommandLineInterface cli, CommandArgument[] args) {
        cli.shell.setScript((String) args[0].getValue());
        return "Скрипт запущен";
    }
}