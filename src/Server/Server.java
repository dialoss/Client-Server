package Server;

import Server.Connection.Request;

public class Server {
    ConnectionManager manager;

    public Server() {
        try {
            NgrokHttpServer.start();
//            manager = new ConnectionManager();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void request(Request request) {
//        Response response = CommandManager.execute(request);
//        manager.response(response);
    }

    public void run() {

    }
}
