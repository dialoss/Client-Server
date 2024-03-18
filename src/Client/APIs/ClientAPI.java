package Client.APIs;

import Common.Connection.Request;
import Common.Connection.Response;

import java.io.IOException;

public abstract class ClientAPI {
    public abstract Response request(Request request) throws IOException;
}