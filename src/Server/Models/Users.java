package Server.Models;

public class Users extends BaseModel {
    @ModelField
    public String login;
    @ModelField
    public String password;
    @ModelField
    public String salt;
}
