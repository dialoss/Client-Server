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
                Response response;
                if (!ConnectionPackage.class.isAssignableFrom(m.getReturnType())) {
                    return Tools.stringify(new ClientResponse(m.invoke(this), ""));
                }
                Class<?>[] params = m.getParameterTypes();
                if (params.length == 0) response = (Response) m.invoke(this);
                else response = (Response) m.invoke(this, Tools.parse(r.data(), params[0]));
                return Tools.stringify(new ClientResponse(response.getBody(), response.getMessage()));
            }
            Map<String, Object> args = new CommandParser().parseArguments(r.method(), (Map<String, String>) Tools.parse(r.data(), Object.class));
            Response response = request.call(new Request(r.method(), args));
            return Tools.stringify(new ClientResponse(response.getBody(), response.getMessage()));
        } catch (RequestError e) {
            throw new RequestError(e.getMessage());
        } catch (Exception e) {
            throw new RequestError(e.getCause().getMessage());
        }
    }

    private void logout() {
        UserManager.clearCookie();
    }

    private UserClient getUser() {
        return UserManager.getClient();
    }

    private Response register(UserInfo data) throws Exception {
        return auth(data, "register");
    }

    private Response auth(UserInfo data, String type) throws Exception {
        UserManager.setClient(new UserClient(data).withId(0));
        Response r = request.call(new Request(type)
                .withArgument("login", data.login())
                .withArgument("name", data.name())
                .withArgument("password", data.password()));
        if (data.remember()) UserManager.setCookie();
        r.setBody(UserManager.getClient());
        return r;
    }

    private Response login(UserInfo data) throws Exception {
        return auth(data, "login");
    }

    private Response schema() {
        return new Response(SchemaGenerator.generate(), Status.OK);
    }
}
