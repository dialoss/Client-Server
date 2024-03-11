package Server;


import Common.EventBus.EventBus;
import Common.Tools;
import Server.Commands.CommandManager;
import Server.Models.Organization;
import Server.Storage.ServerStorage;
import org.json.simple.JSONObject;

public class Server {
    ServerStorage storage;

    public Server() {
        this.storage = new ServerStorage();
        Organization o = new Organization();
        JSONObject x = Tools.objectToJSON(o);
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
