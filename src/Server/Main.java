package Server;

import Client.Client;
import Server.Storage.StorageConnector;

public class Main {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.run();

                Client client = new Client();
                client.start();
            }
        };
        Thread t = new Thread(task);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String path = StorageConnector.saveFile("session_data.json");
        System.out.println("Exiting abnormally. Your collection was saved to " + path);
    }
}
