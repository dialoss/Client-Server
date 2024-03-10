package Server.Commands;

public class Insert extends Command implements IOutputCommand {
    public Insert() {
        super("insert", "Добавляет новый элемент с заданным ключом");
    }

    @Override
    public String execute() {
        return "HELLO";
    }
}
