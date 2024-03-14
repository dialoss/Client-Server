package Client;

import Client.Shell.Form;
import Client.Shell.IForm;
import Common.Exceptions.CommandNotFound;
import Common.Exceptions.InvalidAmountOfArguments;
import Common.Exceptions.InvalidValue;
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
                if (tokens.length <= j)
                    if (arg.required) {
                        throw new InvalidAmountOfArguments("¬ведите аргумент %s - %s".formatted(arg.getName(), arg.type.getSimpleName()));
                    } else continue;
                this.shellForm.putInput(tokens[j++]);
            }
        }
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            if (!arg.required && i >= j) {
                arguments[i++].setValue(arg.defaultValue);
                continue;
            }
            Object value = new Form(arg, this.shellForm).get();
            if (arg.possibleValues.size() != 0 &&
                    arg.possibleValues.stream()
                            .filter(v -> (v.equals(value)))
                            .toArray().length == 0)
                throw new InvalidValue("«начение %s не может быть аргументом %s".formatted(value, arg.getName()));
            arguments[i++].setValue(value);
        }
        return arguments;
    }

    public Request parse(String[] tokens) {
        String commandName = tokens[0];
        Command cmd = CommandManager.get(commandName);
        if (cmd == null) throw new CommandNotFound();
        CommandArgument[] arguments = new CommandArgument[0];
        try {
            arguments = this.getArguments(cmd, Arrays.copyOfRange(tokens, 1, tokens.length));
        } catch (Exception e) {
            this.shellForm.clearInput();
            throw e;
        }
        return new Request(cmd, arguments);
    }
}
