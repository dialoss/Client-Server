package Server.Internal;

import Common.Connection.Request;
import Common.Connection.User;
import Server.Commands.List.CommandArgument;
import Server.Models.MObject;
import Server.Models.Users;
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

    public static User register(Request request) throws Exception {
        CommandArgument[] arguments = request.getArguments();
        String login = (String) arguments[0].getValue();
        String password = (String) arguments[1].getValue();
        String salt = JRand.string().range(50, 100).gen();
        String encrypted = encrypt(password, salt);

        DBOperations session = StorageConnector.dbManager.getSession();
        session.insert(Users.class, new Users().from(new MObject(Map.of(
                "login", login,
                "password", encrypted,
                "salt", salt
        ))));
        return new User(login, encrypted, request.getClient().sessionId);
    }

    public static boolean login(User user) throws SQLException {
        DBOperations session = StorageConnector.dbManager.getSession();
        if (user.getLogin().length() == 0 || user.getPassword().length() == 0) return false;
        MObject[] result = session.get(Users.class, "login = '%s'".formatted(user.getLogin()));
        if (result.length == 0) return false;
        Users userData = (Users) new Users().from(result[0]);
        String stored = userData.password;
        String provided = encrypt(user.getPassword(), userData.salt);
        return stored.equals(provided);
    }
}
