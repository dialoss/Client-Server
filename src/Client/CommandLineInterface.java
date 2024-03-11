package Client;

import Client.Commands.ClientCommand;
import Client.Commands.HistoryEntry;
import Client.Shell.Shell;
import Common.Form;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Commands.List.CommandArgument;
import Server.Models.Organization;
import Server.Connection.Request;
import Common.Exceptions.CommandNotFound;
import Common.Exceptions.ScriptRuntimeException;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandLineInterface extends UserInterface {
    public Shell shell;
    List<HistoryEntry> history;

    CommandLineInterface() {
        this.api = new ClientAPI(this::processOutput);
        this.shell = new Shell();
        this.history = new ArrayList<>();
    }

    private CommandArgument[] getArguments(Command command) {
        CommandArgument[] arguments = Arrays.copyOf(command.getArguments(), command.getArguments().length);
        int i = 0;
        for (CommandArgument arg : command.getArguments()) {
            Object value = new Form(arg, shell).get();
            if (value instanceof JSONObject) {
                value = new Organization().from((JSONObject) value, false);
            }
            arguments[i++].setValue(value);
        }
        return arguments;
    }

    private void execute(String commandName) {
        Command command = CommandManager.get(commandName);
        if (command == null) throw new CommandNotFound();
        CommandArgument[] arguments = this.getArguments(command);
        if (ClientCommand.class.isAssignableFrom(command.getClass())) {
            this.processOutput(((ClientCommand) command).execute(this, arguments));
        } else {
            Request r = new Request(command, arguments);
            this.api.request(r);
            this.history.add(new HistoryEntry(r));
        }
    }

    private void processOutput(Object data) {
        this.shell.print((String) data);
    }

    private void processInput(String commandName) {
        try {
            this.execute(commandName);
        } catch (ScriptRuntimeException e) {
            this.shell.stopScript();
        } catch (RuntimeException e) {
            this.shell.error(e.toString());
        }
    }

    public void start() {
        this.shell.start(this::processInput);
    }

    public List<HistoryEntry> getHistory() {
        return this.history;
    }
}
