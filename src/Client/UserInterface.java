package Client;

import Client.Exceptions.RequestError;
import Client.Exceptions.ServerNotAvailableException;
import Common.Connection.ICallback;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;

public abstract class UserInterface {
    protected ICallback<Request> requestCallback;

    public abstract void start();

    public void setRequestCallback(ICallback<Request> callback) {
        this.requestCallback = (Request request) -> {
            request.setUserClient(UserManager.getClient());
            if (UserManager.getClient().getId() != -1)
                request.setArgument("Authorization", "true");

            Response r = callback.call(request);
            if (r == null) throw new ServerNotAvailableException();
            if (r.code != Status.OK) throw new RequestError(r.getMessage());
            UserManager.setClient(r.getClient());
            return r;
        };
    }
}