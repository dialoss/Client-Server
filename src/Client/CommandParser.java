package Client;

import Client.Shell.Form;
import Client.Shell.IForm;
import Common.Exceptions.CommandNotFound;
import Common.Exceptions.InvalidAmountOfArguments;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Commands.List.ArgumentPosition;
import Server.Commands.List.CommandArgument;
import Server.Connection.Request;

import java.util.Arrays;

public class CommandParser {
    private final IForm shellForm;

    public CommandParser(IForm form) {
        this.shellForm = form;
    }

    private CommandArgument[] getArguments(Command command, String[] tokens) {
        CommandArgument[] arguments = Arrays.copyOf(command.getArguments(), command.getArguments().length);
        int j = 0;
        for (CommandArgument arg : command.getArguments()) {
            if (arg.position == ArgumentPosition.LINE) {
                if (tokens.length <= j) throw new InvalidAmountOfArguments();
                this.shellForm.putInput(tokens[j++]);
            }
        }
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            Object value = new Form(arg, this.shellForm).get();
            arguments[i++].setValue(value);
        }
        return arguments;
    }

    public Request parse(String[] tokens) {
        String commandName = tokens[0];
        Command cmd = CommandManager.get(commandName);
        if (cmd == null) throw new CommandNotFound();
        CommandArgument[] arguments = this.getArguments(cmd, Arrays.copyOfRange(tokens, 1, tokens.length));
        return new Request(cmd, arguments);
    }
}
