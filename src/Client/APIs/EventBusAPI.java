package Client.APIs;

import Common.EventBus.EventBus;
import Server.Connection.Request;
import Server.Connection.Response;

public class EventBusAPI extends ClientAPI {
    public void request(Request request) {
        EventBus.on("response", response -> this.responseCallback.call((Response) response));
        EventBus.emit("request", request);
    }
}
