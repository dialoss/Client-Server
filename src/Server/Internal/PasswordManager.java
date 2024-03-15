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
import java.util.Arrays;
import java.util.Map;

public class PasswordManager {
    private static final MessageDigest md;
    private static String pepper = "2281337";

    static {
        try {
            md = MessageDigest.getInstance("SHA-384");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encrypt(String data, String salt) {
        byte[] hash = md.digest((pepper + data + salt).getBytes());
        return Arrays.toString(hash);
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
        MObject[] result = session.get(Users.class, "login = " + user.getLogin());
        if (result.length == 0) return false;
        Users userData = (Users) new Users().from(result[0]);
        return (encrypt(userData.password, userData.salt).equals(user.getPassword()));
    }
}
