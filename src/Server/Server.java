package Server;


import Common.EventBus.EventBus;
import Server.Storage.ServerStorage;

public class Server {
    ServerStorage storage;

    public Server() {
        this.storage = new ServerStorage();
        CommandManagerBuilder.build();
    }

    private void request(Object data) {
        try {
            Request request = (Request) data;
            CommandManager.execute(request.getName());
        } catch (Exception e) {}
    }

    public void run() {
        EventBus.on("request", this::request);
    }
}
