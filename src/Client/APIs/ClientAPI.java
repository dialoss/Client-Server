package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;

public abstract class ClientAPI {
    public abstract Response request(Request request) throws Exception;
}