package Server.Connection.Managers;

import Common.EventBus.Callback;
import Server.Connection.Request;
import Server.Connection.Response;

import java.util.HashMap;

public abstract class ConnectionManager {
    public HashMap<Integer, Object> clients = new HashMap<>();
    public Callback<Request> requestCallback;

    public abstract void response(Response response);

    public abstract void run();

    public void setRequestCallback(Callback<Request> requestCallback) {
        this.requestCallback = requestCallback;
    }
}