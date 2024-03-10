package Server.Commands;

import Server.CommandManager;

public class Help extends Command implements IOutputCommand {
    public Help() {
        super("help", "������� ������� �� ��������� ��������");
    }

    @Override
    public String execute() {
        String info = "";
        for (Command command : CommandManager.get().values()) {
            info = info.concat(command.getDescription() + "\n");
        }
        return info;
    }
}
