package Server.Commands;

import Server.Models.Organization;
import Server.Storage.CollectionManager;

public class Show extends Command {
    public Show() {
        super("show", "Выводит все элементы коллекции в строковом представлении");
    }

    @Override
    public String execute(CollectionManager manager, String[] args) {
        String result = "";
        for (Organization org : manager.getAll()) {
            result = result.concat(org.toString() + "\n");
        }
        return result;
    }
}
