package Common.Models;

public class Coordinates extends BaseModel {
    @ModelField
    public Double x; //Field cannot be null
    @ModelField
    public Double y; //Field cannot be null
    public static final Class<?> organization = Organization.class;
}