package Client.GUI;

public record UserInfo(String name, String login, String password, Boolean remember) {
    public UserInfo(String name, String login) {
        this(name, login, "", false);
    }
    public UserInfo(String name, String login, String password) {
        this(name, login, password, false);
    }
}
