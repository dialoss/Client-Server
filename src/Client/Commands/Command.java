package Client.Commands;

interface IEffectCommand {
    boolean execute();
}

interface IOutputCommand {
    String execute();
}

public class Command {
    public String description() {
        return "";
    }
}


