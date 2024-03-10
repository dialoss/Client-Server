package Client;


import Client.Commands.Command;
import Client.Commands.Help;
import Client.Commands.Info;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    Map<String, Command> commands;

    CommandManager() {
        this.commands = new HashMap<>();
    }

    public Map<String, Command> get() {
        return this.commands;
    }

    public void add(String name, Type className) {
//        Class classTemp = Class.forName(className);

//        Object obj =classTemp.newInstance();
//        this.commands.put(name, command);
    }
}

class CommandManagerBuilder {
    public void build(CommandManager manager) {
        manager.add("info", Info.class);
        manager.add("help", Help.class);
    }
}
