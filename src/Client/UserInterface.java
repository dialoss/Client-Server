package Client;

import Common.EventBus.Callback;
import Server.Connection.Request;
import Server.Connection.Response;

public interface UserInterface {
    void start();
    Callback<Response> getInterface();
    void setRequestCallback(Callback<Request> callback);
}