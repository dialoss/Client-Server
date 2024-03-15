package Server.Models;

public class Users extends BaseModel {
    @ModelField
    public String name;
    @ModelField
    public String password;
    @ModelField
    public String salt;
}
