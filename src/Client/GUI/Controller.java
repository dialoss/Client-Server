package Client.GUI;

import Common.Tools;
import Server.Data.MObject;
import Server.Data.Models.Organization;
import Server.Storage.StorageConnector;
import javafx.scene.web.WebEngine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

class UserLogin {
    String login;
    String password;
}

public class Controller {
    WebEngine engine;

    Controller(WebEngine engine) {
        this.engine = engine;
    }

    public String call(String methodName, String data) {
        try {
            for (Method m : this.getClass().getDeclaredMethods()) {
                if (!m.getName().equals(methodName)) continue;
                Class<?>[] params = m.getParameterTypes();
                if (params.length == 0) return Tools.stringify(m.invoke(this));
                return Tools.stringify(m.invoke(this, Tools.parse(data, params[0])));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "{}";
    }

    private void log(String d) {
        System.out.println(d);
    }

    private MObject[] getRows() {
        System.out.println("AUE");
        try {
            return StorageConnector.dbManager.getSession().getAll(Organization.class);
        } catch (Exception e) {
            return null;
        }
    }

    private String[] getColumns() {
        return Stream.of(Organization.class.getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }

    private void login(UserLogin data) {

    }
}
