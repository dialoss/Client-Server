package Server.Commands;

public class Info extends Command implements IOutputCommand {
    public Info() {
        super("info", "Выводит информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public String execute() {
        return "HELLO";
    }
}
