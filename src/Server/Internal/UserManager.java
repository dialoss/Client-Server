package Server.Internal;

import Common.Connection.UserClient;

public class UserManager {
    private static UserClient client = new UserClient();

    public static UserClient getClient() {
        return client;
    }

    public static void setClient(UserClient client) {
        UserManager.client = client;
    }
    public static int getClientId() {
        return client.getId();
    }
}
