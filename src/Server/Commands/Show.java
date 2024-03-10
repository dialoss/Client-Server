package Server.Commands;

import Server.Request;

public class Show extends Command implements IRequestCommand {
    public Show() {
        super("show", "Выводит все элементы коллекции в строковом представлении");
    }

    @Override
    public Request execute() {
        return null;
    }
}
