package Server;

import Server.Commands.CommandManager;
import Server.ConnectionManagers.ConnectionManager;
import Server.ConnectionManagers.EventBusConnection;
import Common.Connection.Request;
import Server.Storage.StorageConnector;

public class Server {
    ConnectionManager manager;

    public Server() {
        StorageConnector.init();
        manager = new EventBusConnection();
        manager.setRequestCallback((Request request) ->
                manager.response(CommandManager.execute(request)));
        manager.run();
    }
}