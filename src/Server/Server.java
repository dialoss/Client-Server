package Server;


import Common.EventBus.EventBus;
import Server.Models.Organization;
import Server.Storage.ServerStorage;

public class Server {
    ServerStorage storage;

    public Server() {
        this.storage = new ServerStorage();
        this.storage.initialize();
    }

    private void request(Object data) {
        try {
            Request request = (Request) data;
            Response response = CommandManager.execute(request);
            EventBus.emit("response", response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        EventBus.on("request", this::request);
    }
}
