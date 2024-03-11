package Client;

import Common.EventBus.Callback;
import Common.EventBus.EventBus;
import Server.Connection.Request;
import Server.Connection.Response;

public class ClientAPI {
    ClientAPI(Callback callback) {
        EventBus.on("response", (Object data) -> {
            Response response = (Response) data;
            callback.call(response.getBody());
        });
    }

    void request(Request request) {
        EventBus.emit("request", request);
    }
}
