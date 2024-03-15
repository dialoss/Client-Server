package Client.APIs;

import Common.EventBus.EventBus;
import Common.Connection.Request;
import Common.Connection.Response;

public class EventBusAPI extends ClientAPI {
    public void request(Request request) {
        EventBus.on("response", response -> this.responseCallback.call((Response) response));
        EventBus.emit("request", request);
    }
}