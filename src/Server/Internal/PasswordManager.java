package Server.Internal;

import Common.Connection.Request;
import Common.Connection.UserClient;
import Server.Commands.List.CommandArgument;
import Server.Models.MObject;
import Server.Models.UserAccount;
import Server.Storage.Database.DBOperations;
import Server.Storage.StorageConnector;
import me.xdrop.jrand.JRand;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

public class PasswordManager {
    private static final MessageDigest md;
    private static final String pepper = "2281337";

    static {
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encrypt(String data, String salt) {
        String input = (pepper + data + salt);
        byte[] inputBytes = input.getBytes();
        md.update(inputBytes);
        byte[] hashBytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static UserClient register(Request request) throws Exception {
        CommandArgument[] arguments = request.getArguments();
        String login = (String) arguments[0].getValue();
        String password = (String) arguments[1].getValue();
        String salt = JRand.string().range(50, 100).gen();
        String encrypted = encrypt(password, salt);

        DBOperations session = StorageConnector.dbManager.getSession();
        session.insert(UserAccount.class, new UserAccount().from(new MObject(Map.of(
                "login", login,
                "password", encrypted,
                "salt", salt
        ))));
        return new UserClient(login, encrypted, request.getClient().sessionId);
    }

    public static boolean login(UserClient user) throws SQLException {
        UserAccount userData = getUser(user);
        if (userData == null) return false;
        String stored = userData.password;
        String provided = encrypt(user.getPassword(), userData.salt);
        return stored.equals(provided) || stored.equals(user.getPassword());
    }

    public static UserAccount getUser(UserClient user) throws SQLException {
        DBOperations session = StorageConnector.dbManager.getSession();
        if (user.getLogin().length() == 0 || user.getPassword().length() == 0) return null;
        MObject[] result = session.get(UserAccount.class, "login = '%s'".formatted(user.getLogin()));
        if (result.length == 0) return null;
        return (UserAccount) new UserAccount().from(result[0]);
    }
}
