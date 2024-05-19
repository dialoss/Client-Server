package Client.Shell;

import Common.Commands.ArgumentPosition;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Exceptions.CommandNotFound;
import Common.Exceptions.InvalidAmountOfArguments;
import Common.Exceptions.InvalidValue;
import Common.Files.JSONStorage;
import Common.Models.MObject;
import Common.Models.Organization;
import Common.Pair;
import Common.Serializer.Serializer;
import Common.Tools;
import Server.Commands.CommandManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Parses command with its arguments from text
 */
public class CommandParser {
    private IForm shellForm = null;

    public CommandParser(IForm form) {
        this.shellForm = form;
    }

    public CommandParser() {

    }

    public Map<String, Object> parseArguments(String commandName, Map<String, String> arguments) {
        return parseArguments(getCommand(commandName), arguments);
    }

    public Map<String, Object> parseArguments(Command command, Map<String, String> arguments) {
        Map<String, Object> parsed = new HashMap<>();
        for (CommandArgument arg : command.getArguments()) {
            Object rawValue = arguments.get(arg.getName());
            if (arg.getName().equals("element")) {
                parsed.put(arg.getName(),
                        new Organization().from(JSONStorage.convertObject((MObject) Tools.parse(Tools.stringify(rawValue), MObject.class))));
                continue;
            }

            String value = null;
            if (rawValue != null) value = rawValue.toString();
            if (arg.required && value == null) {
                if (arg.position == ArgumentPosition.MULTILINE) {
                    parsed.put(arg.getName(), new Form(arg, this.shellForm).get());
                    continue;
                }
                throw new InvalidAmountOfArguments("Enter argument %s - %s".formatted(arg.getName(), arg.type.getSimpleName()));
            }
            if (!arg.required && value == null) {
                parsed.put(arg.getName(), arg.defaultValue);
                continue;
            }
            Object parsedValue = Serializer.serializeValue(arg.type, value);
            if (arg.possibleValues.size() != 0 && arg.possibleValues.stream().filter(v -> (v.equals(parsedValue))).toArray().length == 0)
                throw new InvalidValue("Value %s cannot be an argument to %s".formatted(value, arg.getName()));
            parsed.put(arg.getName(), parsedValue);
        }
        return parsed;
    }

    private Map<String, Object> getArguments(Command command, String[] tokens) {
        int j = 0;
        Map<String, String> rawArguments = new HashMap<>();

        for (CommandArgument arg : command.getArguments()) {
            if (arg.position == ArgumentPosition.LINE) {
                if (j < tokens.length) rawArguments.put(arg.getName(), tokens[j++]);
            }
        }
        return parseArguments(command, rawArguments);
    }

    public Command getCommand(String name) {
        Command cmd = CommandManager.get(name);
        if (cmd == null) throw new CommandNotFound();
        return cmd;
    }

    public Pair<Command, Map<String, Object>> parse(String[] tokens) {
        Command cmd = getCommand(tokens[0]);
        Map<String, Object> arguments;
        try {
            arguments = this.getArguments(cmd, Arrays.copyOfRange(tokens, 1, tokens.length));
        } catch (Exception e) {
            throw e;
        }
        return new Pair<>(cmd, arguments);
    }
}