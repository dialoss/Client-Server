package Server;

import Server.Commands.CommandExecutor;
import Server.ConnectionManagers.ConnectionManager;
import Server.ConnectionManagers.EventBusConnection;
import Server.Storage.StorageConnector;

public class Server {
    ConnectionManager manager;

    public Server() {
        System.out.println("Server started");
        StorageConnector.init();
        manager = new EventBusConnection();
        manager.setRequestCallback(CommandExecutor::execute);
    }

    public void run() {
        manager.run();
    }
}