package Common.Models;


public class UserAccount extends BaseModel {
    @ModelField
    public String name;
    @ModelField
    public String login;
    @ModelField
    public String password;
    @ModelField
    public String salt;
}
