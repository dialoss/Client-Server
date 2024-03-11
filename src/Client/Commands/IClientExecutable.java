package Client.Commands;

import Client.CommandLineInterface;
import Client.Shell.Shell;
import Server.Commands.List.CommandArgument;
import Server.Storage.CollectionManager;

public interface IClientExecutable {
    String execute(CommandLineInterface cli, CommandArgument[] args);
}
