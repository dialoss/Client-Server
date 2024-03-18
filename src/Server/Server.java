package Server;

import Server.Commands.CommandExecutor;
import Server.ConnectionManagers.ConnectionManager;
import Server.ConnectionManagers.HTTPConnection;
import Server.Storage.StorageConnector;

public class Server {
    ConnectionManager manager;

    public Server() {
        System.out.println("Server started");
        StorageConnector.init();
        manager = new HTTPConnection();
        manager.setRequestCallback(CommandExecutor::execute);
        new Thread(() -> manager.run()).start();
    }
}