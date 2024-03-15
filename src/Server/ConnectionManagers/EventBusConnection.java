package Server.ConnectionManagers;

import Common.EventBus.EventBus;
import Common.Connection.Request;
import Common.Connection.Response;

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