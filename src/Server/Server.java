package Server;


import Common.EventBus.EventBus;
import Server.Commands.CommandManager;
import Server.Connection.Request;
import Server.Connection.Response;
import Server.Storage.ServerStorage;

public class Server {
    ServerStorage storage;

    public Server() {
        this.storage = new ServerStorage();
    }

    private void request(Object data) {
        Request request = (Request) data;
        Response response = CommandManager.execute(request);
        EventBus.emit("response", response);
    }

    public void run() {
        EventBus.on("request", this::request);
    }
}
