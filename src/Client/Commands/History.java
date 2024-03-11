package Client.Commands;

import Client.CommandLineInterface;
import Server.Commands.List.CommandArgument;

public class History extends ClientCommand {
    public History() {
        super("history", "Выводит историю команд");
    }

    @Override
    public String execute(CommandLineInterface cli, CommandArgument[] args) {
        StringBuilder b = new StringBuilder();
        cli.getHistory().stream()
                .map((HistoryEntry h) -> h.request.getName() + " Статус " + h.response.code)
                .forEach(b::append);
        return b.toString();
    }
}
