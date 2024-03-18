package Client.GUI;

import Common.Connection.ICallback;
import Common.Connection.Request;
import Common.Connection.Response;
import Common.Connection.Status;
import Common.Models.MObject;
import Common.Models.Organization;
import Common.Tools;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

class UserLogin {
    String login;
    String password;
}

public class Controller {
    ICallback<Request> request;

    Controller(ICallback<Request> request) {
        this.request = request;
    }

    public String call(String methodName, String data) {
        try {
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (!m.getName().equals(methodName)) continue;
                Class<?>[] params = m.getParameterTypes();
                if (params.length == 0) return Tools.stringify(m.invoke(this));
                return Tools.stringify(m.invoke(this, Tools.parse(data, params[0])));
            }
            request.call(new Request(methodName, new Object[]{100}));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return "{}";
    }

    private void log(String d) {
        System.out.println(d);
    }

    private MObject[] getRows() throws IOException {
        Response r = request.call(new Request("db", new Object[]{"getAll"}));
        if (r.code == Status.OK) return (MObject[]) r.getBody();
        else return null;

    }

    private String[] getColumns() {
        return Stream.of(Organization.class.getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }

    private String login(UserLogin data) throws IOException {
        Response r = request.call(new Request("login", new Object[]{
                data.login,
                data.password,
        }));
        return (String) r.getBody();
    }

}
