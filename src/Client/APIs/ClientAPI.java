package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;
import Common.EventBus.Callback;

import java.io.IOException;

public abstract class ClientAPI {
    public Callback<Response> responseCallback;

    public abstract void request(Request request) throws IOException;

    public void setResponseCallback(Callback<Response> responseCallback) {
        this.responseCallback = responseCallback;
    }
}