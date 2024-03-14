package Client.APIs;

import Common.EventBus.Callback;
import Server.Connection.Request;
import Server.Connection.Response;

import java.io.IOException;

public abstract class ClientAPI {
    public Callback<Response> responseCallback;

    public abstract void request(Request request) throws IOException;

    public void setResponseCallback(Callback<Response> responseCallback) {
        this.responseCallback = responseCallback;
    }
}
