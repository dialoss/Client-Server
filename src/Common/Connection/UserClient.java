package Common.Connection;

import Client.GUI.UserInfo;

import java.io.Serializable;

public class UserClient implements Serializable {
    private Integer id = -1;
    private String password = "";
    private String login = "";
    private String name = "";
    public Integer port = 0;

    public UserClient() {
    }

    public UserClient(UserInfo info) {
        this.login = info.login();
        this.password = info.password();
        this.name = info.name();
    }

    public UserClient withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }
}
