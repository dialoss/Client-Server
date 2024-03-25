package Server;

import Server.ConnectionManagers.ConnectionManager;
import Server.Storage.StorageConnector;

import java.io.IOException;

public class Server {
    ConnectionManager manager;

    public Server() {
        System.out.println("Server started");
        StorageConnector.init();
        try {
            Test manager = new Test();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        manager.setRequestCallback(CommandExecutor::execute);

    }

    public void run() {
        manager.run();
    }
}