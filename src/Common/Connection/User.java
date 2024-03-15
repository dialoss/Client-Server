package Common.Connection;

public class User {
    public Integer sessionId;
    private String password = "";
    private String login = "";
    public boolean needUpdate = false;

    User(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public User() {
        this.sessionId = (int) (Math.random() * 1e9);
    }

    public User(String login, String password, Integer sessionId) {
        this.login = login;
        this.password = password;
        this.sessionId = sessionId;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}
