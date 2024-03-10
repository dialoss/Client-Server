package Client;

public class Shell {
    CommandManager commandManager;

    Shell(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    void out(String data) {
        System.out.println(data);
    }

    String input() {
        return "";
    }
}
