import Client.Client;
import Server.Server;

public class Main {
    public static void main(String[] args) {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

        Client client = new Client();
        client.run();
    }
}
