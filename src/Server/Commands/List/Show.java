package Server.Commands.List;

import Server.Commands.Command;
import Server.Commands.ServerCommand;
import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Show extends ServerCommand {
    public Show() {
        super("show", "Выводит все элементы коллекции в строковом представлении");
    }

    @Override
    public String execute(CollectionManager manager, CommandArgument[] args) {
        String result = "";
        for (Organization org : manager.getAll()) {
            result = result.concat(org.toString() + "\n");
        }
        if (result.equals("")) return "Нет элементов";
        return result;
    }
}
