package Server;

import Server.Commands.CommandExecutor;
import Server.ConnectionManagers.ConnectionManager;
import Server.ConnectionManagers.TCPConnection;
import Server.Storage.StorageConnector;

public class Server {
    ConnectionManager manager;

    public Server() {
        System.out.println("Server started");
        StorageConnector.init();
        manager = new TCPConnection();
        manager.setRequestCallback(CommandExecutor::execute);
    }

    public void run() {
        manager.run();
    }
}