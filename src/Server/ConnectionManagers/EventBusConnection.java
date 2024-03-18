package Server.ConnectionManagers;

import Common.Connection.Request;
import Common.EventBus;

public class EventBusConnection extends ConnectionManager {
    @Override
    public void run() {
        EventBus.on("request", request ->
               this.requestCallback.call((Request) request));
    }
}