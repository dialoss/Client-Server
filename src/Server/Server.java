package Server;

import Server.Commands.CommandManager;
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
    }
}