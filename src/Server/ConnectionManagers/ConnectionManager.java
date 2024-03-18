package Server.ConnectionManagers;

import Common.Connection.Request;
import Common.Connection.ICallback;

public abstract class ConnectionManager {
    public ICallback<Request> requestCallback;

    public abstract void run();

    public void setRequestCallback(ICallback<Request> requestCallback) {
        this.requestCallback = requestCallback;
    }
}