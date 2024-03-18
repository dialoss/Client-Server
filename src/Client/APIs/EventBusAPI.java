package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;
import Common.EventBus;

import java.io.IOException;

public class EventBusAPI extends ClientAPI {
    public Response request(Request request) {
        try {
            return EventBus.emit("request", request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}