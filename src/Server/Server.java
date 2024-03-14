package Server;

import Server.Commands.Command;
import Server.Commands.CommandManager;
import Server.Commands.List.CommandArgument;
import Server.Connection.Managers.ConnectionManager;
import Server.Connection.Managers.EventBusConnection;
import Server.Connection.Request;

public class Server {
    ConnectionManager manager;

    public Server() {
        manager = new EventBusConnection();
        manager.setRequestCallback((Request request) ->
                manager.response(CommandManager.execute(request)));
        manager.run();

        CommandArgument a = new CommandArgument("filename", String.class);
        a.setValue("a");
        Command c = CommandManager.get("script");
//        EventBus.emit("request", new Request(c, new CommandArgument[]{
//                a
//        }));
    }
}
