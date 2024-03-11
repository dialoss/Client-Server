package Client;

import Common.EventBus.Callback;
import Common.EventBus.EventBus;
import Server.Connection.Request;
import Server.Connection.Response;

public class ClientAPI {
    ClientAPI(Callback<Response> callback) {
        EventBus.on("response", response ->
            callback.call((Response) response)
        );
    }

    void request(Request request) {
        EventBus.emit("request", request);
    }
}
