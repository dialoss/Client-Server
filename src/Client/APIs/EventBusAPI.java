package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;
import Common.EventBus;

public class EventBusAPI extends ClientAPI {
    public Response request(Request request) throws Exception {
        return EventBus.emit("request", request);
    }

    @Override
    public void connect() {

    }
}