package Server.Models;

public class Address extends BaseModel {
    @ModelField(NULL = true, MAX_LENGTH = 50)
    public String zipCode; //Length of the string must not be greater than 15, Field may be null
    @ModelField
    public Location town; //Field cannot be null
    public static final Class<?> organization = Organization.class;
}