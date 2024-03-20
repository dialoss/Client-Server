package Client;

import Client.GUI.UserInfo;
import Client.Storage.LocalStorage;
import Common.Connection.UserClient;

public class UserManager {
    private static UserClient client;

    public static void init() {
        String[] cookie = LocalStorage.read("cookie").split(" ");
        if (cookie.length == 3) {
            client = new UserClient(new UserInfo(cookie[0], cookie[0], cookie[1])).withId(Integer.parseInt(cookie[2]));
            System.out.println("Loaded cookie");
        }
        else client = new UserClient();
    }

    public static void setCookie() {
        LocalStorage.write("cookie", "%s %s %d".formatted(client.getLogin(), client.getPassword(), client.getId()));
    }

    public static void clearCookie() {
        LocalStorage.write("cookie", "");
    }

    public static void setClient(UserClient client) {
        UserManager.client = client;
    }

    public static UserClient getClient() {
        return client;
    }
}
