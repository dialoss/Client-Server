//import Client.Client;
//import Server.Server;

import Client.Commands.Info;

public class Main {
    public static void main() {
//        Server server = new Server();
//        Client client = new Client();
//
//        server.run();
//        client.run();
        Class<Info> s = Info.class;
        try {
            s.getDeclaredConstructor().newInstance();
        } catch (Exception ignored) {

        }
    }
}
