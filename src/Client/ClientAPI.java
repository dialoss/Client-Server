package Client;

import Common.EventBus.Callback;
import Common.EventBus.EventBus;
import Server.Connection.Request;
import Server.Connection.Response;

import java.io.IOException;

public class ClientAPI {
    ConnectionManager manager;

    ClientAPI(Callback<Response> callback) {
        try {
            manager = new ConnectionManager();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void request(Request request) {
        try {
            manager.request(request);
            Thread.sleep(2000);
            manager.response();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
