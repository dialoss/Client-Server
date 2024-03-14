package Server;

import Server.Commands.CommandManager;
import Server.Connection.Managers.ConnectionManager;
import Server.Connection.Managers.HTTPConnection;
import Server.Connection.Request;

public class Server {
    ConnectionManager manager;

    public Server() {
        manager = new HTTPConnection();
        manager.setRequestCallback((Request request) ->
                manager.response(CommandManager.execute(request)));
        manager.run();
    }
}