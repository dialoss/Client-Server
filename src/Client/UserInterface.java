package Client;

import Common.Connection.Request;
import Common.Connection.Response;
import Common.EventBus.Callback;

public abstract class UserInterface {
    protected Callback<Request> requestCallback;

    public abstract void start();
    public abstract Callback<Response> getInterface();

    public void setRequestCallback(Callback<Request> callback) {
        this.requestCallback = callback;
    }
}