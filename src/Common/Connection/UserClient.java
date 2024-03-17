package Common.Connection;

import java.io.Serializable;

public class UserClient implements Serializable {
    public Integer sessionId;
    private String password = "";
    private String login = "";
    public boolean needUpdate = false;

    UserClient(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public UserClient() {
        setSessionId();
    }

    private void setSessionId() {
        this.sessionId = (int) (Math.random() * 1e9);
    }

    public UserClient(String login, String password) {
        this.login = login;
        this.password = password;
        setSessionId();
    }

    public UserClient(String login, String password, Integer sessionId) {
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
