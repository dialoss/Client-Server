package Client;

import Client.Shell.Form;
import Client.Shell.IForm;
import Common.Exceptions.CommandNotFound;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Commands.List.CommandArgument;
import Server.Connection.Request;

import java.util.Arrays;

public class CommandParser {
    private final IForm shellForm;

    public CommandParser(IForm form) {
        this.shellForm = form;
    }

    private CommandArgument[] getArguments(Command command) {
        CommandArgument[] arguments = Arrays.copyOf(command.getArguments(), command.getArguments().length);
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            Object value = new Form(arg, this.shellForm).get();
            arguments[i++].setValue(value);
        }
        return arguments;
    }

    public Request parse(String command) {
        Command cmd = CommandManager.get(command);
        if (cmd == null) throw new CommandNotFound();
        CommandArgument[] arguments = this.getArguments(cmd);
        return new Request(cmd, arguments);
    }
}
