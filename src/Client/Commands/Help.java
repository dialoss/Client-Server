package Client.Commands;

import Client.CommandManager;

public class Help extends Command implements IOutputCommand {
    CommandManager manager;
    public Help(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute() {
        String info = "";
        for (Command command : manager.get().values()) {
            info = info.concat(" " + command.description());
        }
        return info;
    }

    @Override
    public String description() {
        return "¬ыводит справку по доступным командам";
    }
}
