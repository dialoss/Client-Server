package Client;

import Common.Connection.Request;
import Common.Connection.ICallback;

public abstract class UserInterface {
    protected ICallback<Request> requestCallback;

    public abstract void start();

    public void setRequestCallback(ICallback<Request> callback) {
        this.requestCallback = (Request request) -> {
            request.setUserClient(UserManager.getClient());
            return callback.call(request);
        };
    }
}