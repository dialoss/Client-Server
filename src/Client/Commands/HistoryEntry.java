package Client.Commands;

import Server.Connection.Request;
import Server.Connection.Response;

public class HistoryEntry {
    public Request request;
    public Response response;

    public HistoryEntry(Request request) {
        this.request = request;
//        this.response = response;
    }
}
