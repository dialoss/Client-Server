package Server.Commands.List;

import Client.Shell.CommandParser;
import Client.Shell.IForm;
import Client.Shell.IOdevice;
import Common.Commands.Command;
import Common.Commands.CommandArgument;
import Common.Connection.Request;
import Common.Connection.UserClient;
import Common.Exceptions.ScriptRuntimeException;
import Common.Pair;
import Server.Commands.CommandExecutor;
import Server.Data.CustomFields.MBoolean;
import Server.Internal.DevNull;
import Server.Internal.FileForm;
import Server.Storage.Collection.CollectionManager;
import Server.Storage.StorageConnector;

import java.util.Map;
import java.util.Scanner;

public class ExecuteScript extends Command {
    public ExecuteScript() {
        super("script", "Read and execute a script from the specified file. The script contains commands in the same form in which the user enters them in interactive mode.",
                new CommandArgument[]{
                        new CommandArgument("filename", String.class),
                        new CommandArgument("show_log", MBoolean.class).withNotRequired(new MBoolean(true))
                });
    }

    @Override
    public String execute(CollectionManager collectionManager, Map<String, Object> args, UserClient client) throws ScriptRuntimeException {
        String filename = (String) args.get("filename");
        Boolean showLog = ((MBoolean) args.get("show_log")).getValue();
        if (DevNull.deviceCounter >= 10) {
            DevNull.deviceCounter = 0;
            return "Recursion limit %s!".formatted(5);
        }
        try {
            String text = StorageConnector.fileStorage.changeSource("scripts/" + filename)._read();
            IOdevice virtual = new DevNull(new Scanner(text));
            IForm form = new FileForm(virtual);
            CommandParser parser = new CommandParser(form);
            virtual.start((String[] command) -> {
                Pair<Command, Map<String, Object>> c = parser.parse(command);
                String result = CommandExecutor.execute(
                        (Request) new Request(c.a, c.b).withHeader("Authorization", "true")).getMessage();
                if (showLog)
                    form.out(result);
            });
        } catch (Exception e) {
            throw new ScriptRuntimeException(e.toString());
        }
        return "Script %s completed".formatted(filename);
    }
}