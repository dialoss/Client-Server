package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;

/**
 * Class for handling request to the server
 */
public abstract class ClientAPI {
    public abstract Response request(Request request) throws Exception;
}