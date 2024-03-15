package Client;

import Common.EventBus.Callback;
import Common.Connection.Request;
import Common.Connection.Response;

public interface UserInterface {
    void start();
    Callback<Response> getInterface();
    void setRequestCallback(Callback<Request> callback);
}