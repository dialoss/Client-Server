package Server;

import Common.Connection.Request;
import Server.Commands.CommandExecutor;
import Server.ConnectionManagers.ConnectionManager;
import Server.ConnectionManagers.TCPConnection;
import Server.Storage.StorageConnector;

public class Server {
    ConnectionManager manager;

    public Server() {
        StorageConnector.init();
        manager = new TCPConnection();
        manager.setRequestCallback((Request request) ->
                manager.response(CommandExecutor.execute(request)));
        new Thread(() -> manager.run()).start();
    }
}