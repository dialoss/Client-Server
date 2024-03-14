package Server.Commands.List;

import Client.CommandParser;
import Client.Shell.IForm;
import Client.Shell.IOdevice;
import Common.Exceptions.ScriptRuntimeException;
import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Internal.DevNull;
import Server.Internal.FileForm;
import Server.Models.MBoolean;
import Server.Storage.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Scanner;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("script", "Read and execute a script from the specified file. The script contains commands in the same form in which the user enters them in interactive mode.",
                new CommandArgument[]{
                        new CommandArgument("filename", String.class),
                        new CommandArgument("show_log", MBoolean.class).withNotRequired(new MBoolean(false))
// new CommandArgument("recursion", Integer.class).withNotRequired(5)
        });
    }

    @Override
    public String execute(CollectionManager collectionManager, CommandArgument[] args) throws ScriptRuntimeException {
        String filename = (String) args[0].getValue();
        Boolean showLog = ((MBoolean) args[1].getValue()).getValue();
        if (DevNull.deviceCounter >= 5) return "Recursion limit %s!".formatted(5);
        try {
            String text = StorageConnector.storage.changeSource("scripts/" + filename)._read();
            IOdevice virtual = new DevNull(new Scanner(text));
            IForm form = new FileForm(virtual);
            CommandParser parser = new CommandParser(form);
            virtual.start((String[] command) -> {
                String result = CommandManager.execute(parser.parse(command)).getBody();
                if (showLog) form.out(result);
            });
        } catch (Exception e) {
            throw new ScriptRuntimeException(e.toString());
        }
        return "Script %s completed".formatted(filename);
    }
}