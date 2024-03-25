package Common.Connection;

import Common.Commands.Command;

import java.util.HashMap;
import java.util.Map;

public class Request extends ConnectionPackage {
    private final Command command;
    Map<String, Object> arguments;

    public Request(String commandName) {
        this.command = new Command(commandName);
        this.arguments = new HashMap<>();
    }

    public Request(String commandName, Map<String, Object> arguments) {
        this.command = new Command(commandName);
        this.arguments = arguments;
    }

    public Request(Command command,  Map<String, Object> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return this.command.getName();
    }
    public Map<String, Object> getArguments() {
        return arguments;
    }


    public Request withArgument(String name, String value) {
        this.arguments.put(name, value);
        return this;
    }
}