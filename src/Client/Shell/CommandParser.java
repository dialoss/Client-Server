package Client.Shell;

import Common.Commands.ArgumentPosition;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Exceptions.CommandNotFound;
import Common.Exceptions.InvalidAmountOfArguments;
import Common.Exceptions.InvalidValue;
import Common.Pair;
import Server.Commands.CommandManager;

import java.util.Arrays;

public class CommandParser {
    private final IForm shellForm;

    public CommandParser(IForm form) {
        this.shellForm = form;
    }

    private Object[] getArguments(Command command, String[] tokens) {
        Object[] arguments = Arrays.copyOf(command.getArguments(), command.getArguments().length);
        int j = 0;
        for (CommandArgument arg : command.getArguments()) {
            if (arg.position == ArgumentPosition.LINE) {
                if (tokens.length <= j)
                    if (arg.required) {
                        throw new InvalidAmountOfArguments("Enter argument %s - %s".formatted(arg.getName(), arg.type.getSimpleName()));
                    } else continue;
                this.shellForm.putInput(tokens[j++]);
            }
        }
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            if (!arg.required && i >= j) {
                arguments[i++] = arg.defaultValue;
                continue;
            }
            Object value = new Form(arg, this.shellForm).get();
            if (arg.possibleValues.size() != 0 &&
                    arg.possibleValues.stream()
                            .filter(v -> (v.equals(value)))
                            .toArray().length == 0)
                throw new InvalidValue("Value %s cannot be an argument to %s".formatted(value, arg.getName()));
            arguments[i++] = value;
        }
        return arguments;
    }

    public Pair<Command, Object[]> parse(String[] tokens) {
        String commandName = tokens[0];
        Command cmd = CommandManager.get(commandName);
        if (cmd == null) throw new CommandNotFound();
        Object[] arguments;
        try {
            arguments = this.getArguments(cmd, Arrays.copyOfRange(tokens, 1, tokens.length));
        } catch (Exception e) {
            this.shellForm.clearInput();
            throw e;
        }
        return new Pair<>(cmd, arguments);
    }
}