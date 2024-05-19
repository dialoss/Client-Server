package Client.GUI;

import Client.Exceptions.RequestError;
import Client.Shell.CommandParser;
import Client.UserManager;
import Common.Connection.*;
import Common.Models.SchemaGenerator;
import Common.Tools;

import java.lang.reflect.Method;
import java.util.Map;

record ClientResponse(Object body, String message) {
}

public class Controller {
    ICallback<Request> request;

    Controller(ICallback<Request> request) {
        this.request = request;
    }

    public String call(BridgeRequest r) throws RequestError {
        try {
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (!m.getName().equals(r.method())) continue;
                if (!ConnectionPackage.class.isAssignableFrom(m.getReturnType())) {
                    return Tools.stringify(new ClientResponse(m.invoke(this), ""));
                }
                Response response = (Response) m.invoke(this);
                return Tools.stringify(new ClientResponse(response.getBody(), response.getMessage()));
            }
            Map<String, Object> args = new CommandParser().parseArguments(r.method(), (Map<String, String>) Tools.parse(r.data(), Object.class));
            Response response = request.call(new Request(r.method(), args));
            return Tools.stringify(new ClientResponse(response.getBody(), response.getMessage()));
        } catch (Exception e) {
            throw new RequestError(e.getMessage());
        }
    }

    private void logout() {
        UserManager.clearCookie();
    }

    private UserClient getUser() {
        return UserManager.getClient();
    }

    private Response schema() {
        return new Response(SchemaGenerator.generate(), Status.OK);
    }
}
