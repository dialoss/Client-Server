package Server.Internal;

import Common.Connection.UserClient;
import Common.Models.MObject;
import Common.Models.UserAccount;
import Server.Storage.StorageConnector;

public class UserManager {
    private static int clientId = -1;
    private static UserClient client = new UserClient();

    public static UserClient getClient() {
        return client;
    }

    public static void setClient(UserClient client) {
        UserManager.client = client;
        MObject dbUser = null;
        try {
            dbUser = StorageConnector.dbManager.getSession()
                    .get(UserAccount.class, "login='%s'".formatted(client.getLogin()))[0];
            setClientId((Integer) dbUser.get("id"));
        } catch (Exception e) {
            setClientId(-1);
        }
    }

    public static void setClientId(int clientId) {
        UserManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
