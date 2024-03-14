package Server.Commands;

import Server.Connection.Request;
import Server.Connection.Response;

public class HistoryEntry {
    public Request request;
    public Response response;

    public HistoryEntry(Request request, Response response) {
        this.request = request;
        this.response = response;
    }
}