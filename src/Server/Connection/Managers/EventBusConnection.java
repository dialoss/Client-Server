package Server.Connection.Managers;

import Common.EventBus.EventBus;
import Server.Connection.Request;
import Server.Connection.Response;

public class EventBusConnection extends ConnectionManager {
    @Override
    public void response(Response response) {
        EventBus.emit("response", response);
    }

    @Override
    public void run() {
        EventBus.on("request", request ->
               this.requestCallback.call((Request) request));
    }
}