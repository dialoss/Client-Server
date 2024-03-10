package Server;


import Common.EventBus.EventBus;
import Server.Models.Organization;
import Server.Storage.ServerStorage;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.Map;

public class Server {
    ServerStorage storage;

    public Server() {
        this.storage = new ServerStorage();
        CommandManagerBuilder.build();
//        Organization test = new Organization(new JSONObject(Map.of(
//                "name", "qweqweq",
//                "annualTurnover", 1.4f,
//                "type", "GOVERNMENT"
//        )));
//        test.validate();
    }

    private void request(Object data) {
        try {
            Request request = (Request) data;
            Response response = CommandManager.execute(request.getName());
            EventBus.emit("response", response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        EventBus.on("request", this::request);
    }
}
