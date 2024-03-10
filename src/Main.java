import Client.Client;
import Server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client();

        server.run();
        client.run();
    }
}
